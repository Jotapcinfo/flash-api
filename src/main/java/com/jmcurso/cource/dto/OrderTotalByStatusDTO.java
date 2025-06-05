package com.jmcurso.cource.dto;

import com.jmcurso.cource.entities.enums.OrderStatus;

public class OrderTotalByStatusDTO {

	private String status;
	private Double total;

	public OrderTotalByStatusDTO(OrderStatus status, Double total) {
		this.status = status.name();
		this.total = total;
	}

	public String getStatus() {
		return status;
	}

	public Double getTotal() {
		return total;
	}
}
