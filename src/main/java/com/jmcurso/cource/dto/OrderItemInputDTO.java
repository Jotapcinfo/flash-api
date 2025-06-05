package com.jmcurso.cource.dto;

import com.jmcurso.cource.entities.OrderItem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderItemInputDTO {

	@NotNull(message = "Produto é obrigatório")
	private Long productId;

	@NotNull(message = "Quantidade é obrigatória")
	@Min(value = 1, message = "Quantidade mínima é 1")
	private Integer quantity;

	@NotNull(message = "Preço é obrigatório")
	@Positive(message = "Preço deve ser positivo")
	private Double price;

	public OrderItemInputDTO() {
	}

	public OrderItemInputDTO(OrderItem item) {

		productId = item.getProduct().getId();
		quantity = item.getQuantity();
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
