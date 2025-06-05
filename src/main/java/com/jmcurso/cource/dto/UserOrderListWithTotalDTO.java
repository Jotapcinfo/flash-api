package com.jmcurso.cource.dto;

import java.util.List;

public class UserOrderListWithTotalDTO {

	private List<OrderResponseDTO> orders;
	private Double grandTotal;

	public UserOrderListWithTotalDTO(List<OrderResponseDTO> orders) {
		this.orders = orders;
		this.grandTotal = orders.stream().mapToDouble(OrderResponseDTO::getTotal).sum();
	}

	public List<OrderResponseDTO> getOrders() {
		return orders;
	}

	public Double getGrandTotal() {
		return grandTotal;
	}
}
