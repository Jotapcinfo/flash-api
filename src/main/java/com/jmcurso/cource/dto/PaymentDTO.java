package com.jmcurso.cource.dto;

import java.time.Instant;

import com.jmcurso.cource.entities.Payment;

public class PaymentDTO {

	private Long id;
	private Instant moment;
	private String paymentMethod;

	public PaymentDTO() {
	}

	public PaymentDTO(Long id, Instant moment, String paymentMethod) {
		super();
		this.id = id;
		this.moment = moment;
		this.paymentMethod = paymentMethod;
	}

	public PaymentDTO(Payment payment) {
		id = payment.getId();
		moment = payment.getMoment();
		paymentMethod = payment.getPaymentMethod().name();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getMoment() {
		return moment;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}
}
