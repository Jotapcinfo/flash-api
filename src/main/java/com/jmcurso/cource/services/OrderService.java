package com.jmcurso.cource.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmcurso.cource.dto.BalanceDetailedDTO;
import com.jmcurso.cource.dto.OrderDTO;
import com.jmcurso.cource.dto.OrderItemInputDTO;
import com.jmcurso.cource.dto.OrderStatusUpdateDTO;
import com.jmcurso.cource.dto.OrderTotalByStatusDTO;
import com.jmcurso.cource.dto.OrderTotalByUserDTO;
import com.jmcurso.cource.dto.OrderTotalReportDTO;
import com.jmcurso.cource.entities.Order;
import com.jmcurso.cource.entities.OrderItem;
import com.jmcurso.cource.entities.Payment;
import com.jmcurso.cource.entities.Product;
import com.jmcurso.cource.entities.User;
import com.jmcurso.cource.entities.enums.OrderStatus;
import com.jmcurso.cource.entities.enums.PaymentMethod;
import com.jmcurso.cource.repositories.OrderRepository;
import com.jmcurso.cource.repositories.ProductRepository;
import com.jmcurso.cource.repositories.UserRepository;
import com.jmcurso.cource.services.exceptions.InvalidOrderStatusException;
import com.jmcurso.cource.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	public List<OrderTotalByStatusDTO> getTotalByStatusReport() {
		Map<OrderStatus, Double> totals = new EnumMap<>(OrderStatus.class);
		for (OrderStatus status : OrderStatus.values()) {
			totals.put(status, 0.0);
		}

		for (Order order : orderRepository.findAll()) {
			OrderStatus status = order.getOrderStatus();
			Double totalAtual = totals.getOrDefault(status, 0.0);
			totals.put(status, totalAtual + order.getTotal());
		}

		return Arrays.stream(OrderStatus.values()).map(status -> new OrderTotalByStatusDTO(status, totals.get(status)))
				.collect(Collectors.toList());
	}

	public Order findById(Long id) {
		return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public List<Order> findByStatus(Integer statusCode) {
		List<Order> list = orderRepository.findByOrderStatus(statusCode);
		if (list.isEmpty()) {
			throw new InvalidOrderStatusException(statusCode);
		}
		return list;
	}

	public Order insert(OrderDTO dto) {
		Order order = new Order();
		order.setMoment(Instant.now());
		order.setOrderStatus(OrderStatus.valueOf(dto.getOrderStatus()));

		User client = userRepository.findById(dto.getClientId())
				.orElseThrow(() -> new ResourceNotFoundException(dto.getClientId()));
		order.setClient(client);

		Order saved = orderRepository.save(order);

		for (OrderItemInputDTO itemDTO : dto.getItems()) {
			Product product = productRepository.findById(itemDTO.getProductId())
					.orElseThrow(() -> new ResourceNotFoundException(itemDTO.getProductId()));

			if (dto.getOrderStatus() != null && OrderStatus.valueOf(dto.getOrderStatus())
					.getCode() >= OrderStatus.APROVADO_PELO_CISCO.getCode()) {
				int novaQuantidade = product.getQuantityInStock() - itemDTO.getQuantity();
				if (novaQuantidade < 0) {
					throw new IllegalStateException("Estoque insuficiente para o produto: " + product.getName());
				}
				product.setQuantityInStock(novaQuantidade);
				productRepository.save(product);
			}

			OrderItem item = new OrderItem(saved, product, itemDTO.getQuantity(), itemDTO.getPrice());
			saved.getItems().add(item);
		}

		if (dto.getOrderStatus() != null
				&& OrderStatus.valueOf(dto.getOrderStatus()).getCode() >= OrderStatus.APROVADO_PELO_CISCO.getCode()) {
			Payment payment = new Payment();
			payment.setMoment(Instant.now());
			payment.setOrder(saved);

			if (dto.getPaymentMethod() != null) {
				try {
					payment.setPaymentMethod(PaymentMethod.valueOf(dto.getPaymentMethod()));
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException("Forma de pagamento inválida: " + dto.getPaymentMethod());
				}
			} else {
				payment.setPaymentMethod(PaymentMethod.CREDITOS_TEMPORAIS);
			}
			saved.setPayment(payment);
		}

		return orderRepository.save(saved);
	}

	public void delete(Long id) {
		if (!orderRepository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		orderRepository.deleteById(id);
	}

	public Order updateStatus(Long id, OrderStatusUpdateDTO dto) {
		Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

		OrderStatus oldStatus = order.getOrderStatus();
		OrderStatus newStatus = OrderStatus.valueOf(dto.getOrderStatus());

		order.setOrderStatus(newStatus);
		order.setMoment(dto.getMoment());

		if (oldStatus != OrderStatus.APROVADO_PELO_CISCO && newStatus == OrderStatus.APROVADO_PELO_CISCO) {
			for (OrderItem item : order.getItems()) {
				Product product = item.getProduct();
				int newStock = product.getQuantityInStock() - item.getQuantity();
				if (newStock < 0) {
					throw new IllegalStateException("Estoque insuficiente para o produto: " + product.getName());
				}
				product.setQuantityInStock(newStock);
				productRepository.save(product);
			}

			if (order.getPayment() == null) {
				Payment payment = new Payment();
				payment.setMoment(Instant.now());
				payment.setOrder(order);
				payment.setPaymentMethod(PaymentMethod.CREDITOS_TEMPORAIS);
				order.setPayment(payment);
			}
		}

		if (oldStatus == OrderStatus.APROVADO_PELO_CISCO && newStatus == OrderStatus.CANCELADO_PELO_TIME_FLASH) {
			for (OrderItem item : order.getItems()) {
				Product product = item.getProduct();
				product.setQuantityInStock(product.getQuantityInStock() + item.getQuantity());
				productRepository.save(product);
			}
		}

		return orderRepository.save(order);
	}

	public OrderTotalByUserDTO getTotalByUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(userId));
		List<Order> orders = orderRepository.findByUserId(userId);

		List<OrderTotalByUserDTO.StatusTotalDTO> statusTotals = new ArrayList<>();
		double grandTotal = 0.0;

		for (OrderStatus status : OrderStatus.values()) {
			double total = orders.stream().filter(o -> o.getOrderStatus() == status).mapToDouble(Order::getTotal).sum();

			statusTotals.add(new OrderTotalByUserDTO.StatusTotalDTO(status.name(), total));
			grandTotal += total;
		}

		return new OrderTotalByUserDTO(user.getId(), user.getName(), statusTotals, grandTotal);
	}

	public OrderTotalReportDTO getTotalByStatus() {
		List<Order> orders = orderRepository.findAll();

		List<OrderTotalReportDTO.StatusTotalDTO> statusTotals = new ArrayList<>();
		double grandTotal = 0.0;

		for (OrderStatus status : OrderStatus.values()) {
			double total = orders.stream().filter(o -> o.getOrderStatus() == status).mapToDouble(Order::getTotal).sum();

			statusTotals.add(new OrderTotalReportDTO.StatusTotalDTO(status.name(), total));
			grandTotal += total;
		}

		return new OrderTotalReportDTO(statusTotals, grandTotal);
	}

	public BalanceDetailedDTO getBalanceReport() {
		double totalEstoque = productRepository.findAll().stream()
				.mapToDouble(p -> p.getPrice() * p.getQuantityInStock()).sum();

		double totalPedidosAprovados = orderRepository.findAll().stream()
				.filter(o -> o.getOrderStatus() == OrderStatus.APROVADO_PELO_CISCO).mapToDouble(Order::getTotal).sum();

		return new BalanceDetailedDTO(totalEstoque, totalPedidosAprovados);
	}
}
