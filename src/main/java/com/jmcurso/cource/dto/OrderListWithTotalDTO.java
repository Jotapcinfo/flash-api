package com.jmcurso.cource.dto;

import java.util.List;

public class OrderListWithTotalDTO {

	private List<OrderResponseDTO> orders;
	private Double grandTotal;

	public OrderListWithTotalDTO(List<OrderResponseDTO> orders, Double grandTotal) {
		this.orders = orders;
		this.grandTotal = grandTotal;
	}

	public List<OrderResponseDTO> getOrders() {
		return orders;
	}

	public Double getGrandTotal() {
		return grandTotal;
	}
}
