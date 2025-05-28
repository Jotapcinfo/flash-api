package com.jmcurso.cource.dto;

import java.time.Instant;

import jakarta.validation.constraints.NotNull;

public class OrderStatusUpdateDTO {

	@NotNull(message = "Status do pedido é obrigatório")
	private Integer orderStatus;

	@NotNull(message = "Data/hora do momento é obrigatória")
	private Instant moment;

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Instant getMoment() {
		return moment;
	}

	public void setMoment(Instant moment) {
		this.moment = moment;
	}
}
