package com.jmcurso.cource.services.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public EmailAlreadyExistsException(String email) {
		super("E-mail já cadastrado: " + email);
	}
}
