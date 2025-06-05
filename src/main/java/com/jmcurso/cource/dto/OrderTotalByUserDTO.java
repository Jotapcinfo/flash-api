package com.jmcurso.cource.dto;

import java.util.List;

public class OrderTotalByUserDTO {

	private Long userId;
	private String userName;
	private List<StatusTotalDTO> statusTotals;
	private Double grandTotal;

	public OrderTotalByUserDTO(Long userId, String userName, List<StatusTotalDTO> statusTotals, Double grandTotal) {
		this.userId = userId;
		this.userName = userName;
		this.statusTotals = statusTotals;
		this.grandTotal = grandTotal;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
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
