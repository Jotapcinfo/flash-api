package com.jmcurso.cource.dto;

public class BalanceReportDTO {

	private double totalEstoque;
	private double totalPedidosAprovados;
	private double balancoGeral;
	private double capitalTotal;

	public BalanceReportDTO(double totalEstoque, double totalPedidosAprovados) {
		this.totalEstoque = totalEstoque;
		this.totalPedidosAprovados = totalPedidosAprovados;
		this.balancoGeral = totalEstoque - totalPedidosAprovados;
		this.capitalTotal = totalEstoque + totalPedidosAprovados;
	}

	public double getTotalEstoque() {
		return totalEstoque;
	}

	public double getTotalPedidosAprovados() {
		return totalPedidosAprovados;
	}

	public double getBalancoGeral() {
		return balancoGeral;
	}

	public double getCapitalTotal() {
		return capitalTotal;
	}
}