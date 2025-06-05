package com.jmcurso.cource.dto;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.jmcurso.cource.entities.Order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OrderDTO {

	private Instant moment;

	@NotNull(message = "Status do pedido é obrigatório")
	private Integer orderStatus;

	@NotNull(message = "ID do cliente é obrigatório")
	private Long clientId;

	@NotEmpty(message = "O pedido deve conter pelo menos um item")
	@Valid
	private List<OrderItemInputDTO> items;

	private Integer paymentMethod;

	private PaymentDTO payment;

	public OrderDTO() {
	}

	public OrderDTO(Instant moment, Integer orderStatus, Long clientId, List<OrderItemInputDTO> items,
			Integer paymentMethod) {
		this.moment = moment;
		this.orderStatus = orderStatus;
		this.clientId = clientId;
		this.items = items;
		this.paymentMethod = paymentMethod;
	}

	public OrderDTO(Order order) {
		moment = order.getMoment();
		orderStatus = order.getOrderStatus().getCode();
		clientId = order.getClient().getId();
		items = order.getItems().stream().map(OrderItemInputDTO::new).collect(Collectors.toList());
		paymentMethod = order.getPaymentMethod() != null ? order.getPaymentMethod().getCode() : null;
		payment = order.getPayment() != null ? new PaymentDTO(order.getPayment()) : null;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public List<OrderItemInputDTO> getItems() {
		return items;
	}

	public void setItems(List<OrderItemInputDTO> items) {
		this.items = items;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public PaymentDTO getPayment() {
		return payment;
	}

	public void setPayment(PaymentDTO payment) {
		this.payment = payment;
	}
}
