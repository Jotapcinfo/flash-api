package com.jmcurso.cource.dto;

import com.jmcurso.cource.entities.OrderItem;

public class OrderItemDTO {

	private Integer quantity;
	private Double price;
	private Double subtotal;
	private ProductMiniDTO product;

	public OrderItemDTO(OrderItem item) {
		this.quantity = item.getQuantity();
		this.price = item.getPrice();
		this.subtotal = item.getSubtotal();
		this.product = new ProductMiniDTO(item.getProduct());
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

	public ProductMiniDTO getProduct() {
		return product;
	}
}
