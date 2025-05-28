package com.jmcurso.cource.services.exceptions;

public class InvalidOrderStatusException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidOrderStatusException(Integer statusCode) {
		super("Status de pedido inválido ou não utilizado: " + statusCode);
	}
}
