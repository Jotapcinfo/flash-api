package com.jmcurso.cource.entities.enums;

public enum OrderStatus {

	PROCESSANDO_EM_LABORATORIO(1), APROVADO_PELO_CISCO(2), ENVIADO_PARA_CAMPO(3), ENTREGUE_AO_METAUMANO(4),
	CANCELADO_PELO_TIME_FLASH(5);

	private int code;

	private OrderStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static OrderStatus valueOf(int code) {
		for (OrderStatus value : OrderStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid OrderStatus code");
	}
}
