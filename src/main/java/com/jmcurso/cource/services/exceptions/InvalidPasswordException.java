package com.jmcurso.cource.services.exceptions;

public class InvalidPasswordException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidPasswordException(String msg) {
		super(msg);
	}
}
