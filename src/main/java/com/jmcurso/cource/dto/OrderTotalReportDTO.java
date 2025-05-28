package com.jmcurso.cource.dto;

import java.util.List;

public class OrderTotalReportDTO {

	private List<StatusTotalDTO> statusTotals;
	private Double grandTotal;

	public OrderTotalReportDTO(List<StatusTotalDTO> statusTotals, Double grandTotal) {
		this.statusTotals = statusTotals;
		this.grandTotal = grandTotal;
	}

	public List<StatusTotalDTO> getStatusTotals() {
		return statusTotals;
	}

	public Double getGrandTotal() {
		return grandTotal;
	}

	public static class StatusTotalDTO {
		private String status;
		private Double total;

		public StatusTotalDTO(String status, Double total) {
			this.status = status;
			this.total = total;
		}

		public String getStatus() {
			return status;
		}

		public Double getTotal() {
			return total;
		}
	}
}
