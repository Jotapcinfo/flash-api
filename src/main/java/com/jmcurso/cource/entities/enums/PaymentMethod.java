package com.jmcurso.cource.entities.enums;

public enum PaymentMethod {

	CREDITOS_TEMPORAIS(1), SINCRONIZACAO_QUANTICA(2);

	private int code;

	private PaymentMethod(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static PaymentMethod valueOf(int code) {
		for (PaymentMethod value : PaymentMethod.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Código inválido para PaymentMethod: " + code);
	}
}
