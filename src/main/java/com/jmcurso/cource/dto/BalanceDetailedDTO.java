package com.jmcurso.cource.dto;

public class BalanceDetailedDTO {

	private Info totalEstoque;
	private Info totalPedidosAprovados;
	private Info balancoGeral;
	private Info capitalTotal;

	public BalanceDetailedDTO(double estoque, double aprovados) {
		this.totalEstoque = new Info(estoque, "Soma total do valor de todos os produtos em estoque atualmente.");
		this.totalPedidosAprovados = new Info(aprovados, "Soma total dos valores dos pedidos com status aprovado.");
		this.balancoGeral = new Info(estoque - aprovados,
				"Diferença entre o estoque e os pedidos aprovados. É o valor líquido ainda não comprometido em pedidos.");
		this.capitalTotal = new Info(estoque + aprovados,
				"Soma do valor em estoque com o valor dos pedidos já aprovados. Representa o valor total que a empresa possui em ativos, somando o que tem em estoque + o que já saiu.");
	}

	public Info getTotalEstoque() {
		return totalEstoque;
	}

	public Info getTotalPedidosAprovados() {
		return totalPedidosAprovados;
	}

	public Info getBalancoGeral() {
		return balancoGeral;
	}

	public Info getCapitalTotal() {
		return capitalTotal;
	}

	public static class Info {
		private double valor;
		private String mensagem;

		public Info(double valor, String mensagem) {
			this.valor = valor;
			this.mensagem = mensagem;
		}

		public double getValor() {
			return valor;
		}

		public String getMensagem() {
			return mensagem;
		}
	}
}
