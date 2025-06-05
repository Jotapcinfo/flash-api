package com.jmcurso.cource.dto;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.jmcurso.cource.entities.Order;
import com.jmcurso.cource.entities.enums.OrderStatus;

public class OrderResponseDTO {

	private Long id;
	private Instant moment;
	private String orderStatus;
	private String statusMessage;
	private String financialMessage;
	private UserDTO client;
	private List<OrderItemResponseDTO> items;
	private PaymentDTO payment;
	private Double total;
	
	public OrderResponseDTO() {
	}

	public OrderResponseDTO(Order order) {
		this.id = order.getId();
		this.moment = order.getMoment();
		this.orderStatus = order.getOrderStatus().name();
		this.statusMessage = gerarMensagemDeStatus(order);
		this.financialMessage = gerarMensagemFinanceira(order);
		this.client = new UserDTO(order.getClient());
		this.items = order.getItems().stream().map(OrderItemResponseDTO::new).collect(Collectors.toList());
		this.total = order.getTotal();
		this.payment = exibirPagamento(order) ? new PaymentDTO(order.getPayment()) : null;
	}

	private String gerarMensagemDeStatus(Order order) {
		switch (order.getOrderStatus()) {
		case PROCESSANDO_EM_LABORATORIO:
			return "O pedido está sendo analisado em laboratório pela STAR Labs.";
		case APROVADO_PELO_CISCO:
			return "Pedido aprovado por Cisco. Pagamento confirmado.";
		case ENVIADO_PARA_CAMPO:
			return "Pedido em rota. Foi enviado para o campo.";
		case ENTREGUE_AO_METAUMANO:
			return "Pedido entregue ao meta-humano com sucesso.";
		case CANCELADO_PELO_TIME_FLASH:
			return "Pedido cancelado pelo Time Flash.";
		default:
			return "Status desconhecido.";
		}
	}

	private String gerarMensagemFinanceira(Order order) {
		switch (order.getOrderStatus()) {
		case PROCESSANDO_EM_LABORATORIO:
			return "Aguardando aprovação do Cisco. Nenhuma ação financeira realizada.";
		case APROVADO_PELO_CISCO:
			return "Pagamento registrado com sucesso. Equipamento liberado para uso.";
		case ENVIADO_PARA_CAMPO:
			return "Pedido em trânsito. Pagamento confirmado.";
		case ENTREGUE_AO_METAUMANO:
			return "Pedido entregue com sucesso ao meta-humano. Encerrado.";
		case CANCELADO_PELO_TIME_FLASH:
			return order.getPayment() != null ? "Pedido cancelado. Pagamento será estornado pelo Time Flash."
					: "Pedido cancelado antes da aprovação. Nenhuma transação foi feita.";
		default:
			return "Status financeiro desconhecido.";
		}
	}

	private boolean exibirPagamento(Order order) {
		return order.getOrderStatus().getCode() >= OrderStatus.APROVADO_PELO_CISCO.getCode()
				&& order.getPayment() != null;
	}

	public Long getId() {
		return id;
	}

	public Instant getMoment() {
		return moment;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public String getFinancialMessage() {
		return financialMessage;
	}

	public UserDTO getClient() {
		return client;
	}

	public List<OrderItemResponseDTO> getItems() {
		return items;
	}

	public PaymentDTO getPayment() {
		return payment;
	}

	public Double getTotal() {
		return total;
	}
}
