package com.jmcurso.cource.dto;

import java.time.Instant;

public class UserPasswordUpdatedResponseDTO {

	private String message;
	private Long userId;
	private Instant timestamp;

	public UserPasswordUpdatedResponseDTO(Long userId, String message) {
		this.userId = userId;
		this.message = message;
		this.timestamp = Instant.now();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
}
