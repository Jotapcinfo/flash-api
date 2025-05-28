package com.jmcurso.cource.dto;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.jmcurso.cource.entities.Order;
import com.jmcurso.cource.entities.OrderItem;
import com.jmcurso.cource.entities.Product;
import com.jmcurso.cource.entities.User;

public class OrderPostResponseDTO {

	private Long id;
	private Instant moment;
	private String orderStatus;
	private ClientDTO client;
	private List<ItemDTO> items;
	private Double total;

	public OrderPostResponseDTO(Order order) {
		this.id = order.getId();
		this.moment = order.getMoment();
		this.orderStatus = order.getOrderStatus().name();
		this.client = new ClientDTO(order.getClient());
		this.items = order.getItems().stream().map(ItemDTO::new).collect(Collectors.toList());
		this.total = order.getTotal();
	}

	public static class ClientDTO {
		private Long id;
		private String name;
		private String email;

		public ClientDTO(User user) {
			this.id = user.getId();
			this.name = user.getName();
			this.email = user.getEmail();
		}

		public Long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getEmail() {
			return email;
		}
	}

	public static class ItemDTO {
		private ProductDTO product;
		private Integer quantity;
		private Double price;
		private Double subtotal;

		public ItemDTO(OrderItem item) {
			this.product = new ProductDTO(item.getProduct());
			this.quantity = item.getQuantity();
			this.price = item.getPrice();
			this.subtotal = item.getSubtotal();
		}

		public ProductDTO getProduct() {
			return product;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public Double getPrice() {
			return price;
		}

		public Double getSubtotal() {
			return subtotal;
		}
	}

	public static class ProductDTO {
		private Long id;
		private String name;

		public ProductDTO(Product product) {
			this.id = product.getId();
			this.name = product.getName();
		}

		public Long getId() {
			return id;
		}

		public String getName() {
			return name;
		}
	}

	public Long getId() {
		return id;
	}

	public Instant getMoment() {
		return moment;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public ClientDTO getClient() {
		return client;
	}

	public List<ItemDTO> getItems() {
		return items;
	}

	public Double getTotal() {
		return total;
	}
}
