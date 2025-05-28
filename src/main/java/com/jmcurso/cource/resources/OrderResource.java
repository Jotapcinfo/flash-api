package com.jmcurso.cource.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmcurso.cource.dto.BalanceDetailedDTO;
import com.jmcurso.cource.dto.OrderDTO;
import com.jmcurso.cource.dto.OrderListWithTotalDTO;
import com.jmcurso.cource.dto.OrderPostResponseDTO;
import com.jmcurso.cource.dto.OrderResponseDTO;
import com.jmcurso.cource.dto.OrderStatusUpdateDTO;
import com.jmcurso.cource.dto.OrderTotalByUserDTO;
import com.jmcurso.cource.dto.OrderTotalReportDTO;
import com.jmcurso.cource.entities.Order;
import com.jmcurso.cource.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

	@Autowired
	private OrderService orderService;

	@Operation(summary = "Retorna todos os pedidos")
	@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
	@GetMapping
	public ResponseEntity<OrderListWithTotalDTO> findAll() {
		List<Order> list = orderService.findAll();
		List<OrderResponseDTO> dtoList = list.stream().map(OrderResponseDTO::new).toList();

		double grandTotal = list.stream().mapToDouble(Order::getTotal).sum();

		OrderListWithTotalDTO result = new OrderListWithTotalDTO(dtoList, grandTotal);
		return ResponseEntity.ok().body(result);
	}

	@Operation(summary = "Busca pedido pelo ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado") })
	@GetMapping(value = "/{id}")
	public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id) {
		Order obj = orderService.findById(id);
		return ResponseEntity.ok().body(new OrderResponseDTO(obj));
	}

	@Operation(summary = "Busca pedidos filtrando por status (Endpoint: /orders/status?status=1)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
			@ApiResponse(responseCode = "404", description = "Nenhum pedido com esse status") })
	@GetMapping("/status")
	public ResponseEntity<List<OrderResponseDTO>> findByStatus(@RequestParam Integer status) {
		List<Order> list = orderService.findByStatus(status);
		List<OrderResponseDTO> dtoList = list.stream().map(OrderResponseDTO::new).toList();
		return ResponseEntity.ok().body(dtoList);
	}

	@Operation(summary = "Relatório: total de pedidos por status (geral) (Endpoint: /orders/report/total-by-status)", description = "Retorna o valor total acumulado de todos os pedidos agrupados por status.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso") })
	@GetMapping("/report/total-by-status")
	public ResponseEntity<OrderTotalReportDTO> getTotalByStatus() {
		OrderTotalReportDTO dto = orderService.getTotalByStatus();
		return ResponseEntity.ok().body(dto);
	}

	@Operation(summary = "Relatório de totais por status dos pedidos de um usuário (Endpoint: /orders/report/total-by-user?userId=1)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Relatório gerado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Requisição inválida (userId ausente ou incorreto)"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
	@GetMapping("/report/total-by-user")
	public ResponseEntity<OrderTotalByUserDTO> getTotalByUser(@RequestParam Long userId) {
		OrderTotalByUserDTO dto = orderService.getTotalByUser(userId);
		return ResponseEntity.ok().body(dto);
	}

	@Operation(summary = "Relatório de balanço geral (estoque - pedidos aprovados) (Endpoint: /orders/report/balance)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Relatório de balanço gerado com sucesso") })
	@GetMapping("/report/balance")
	public ResponseEntity<BalanceDetailedDTO> getBalanceReport() {
		BalanceDetailedDTO dto = orderService.getBalanceReport();
		return ResponseEntity.ok().body(dto);
	}

	@Operation(summary = "Cria um novo pedido")
	@ApiResponse(responseCode = "201", description = "Pedido criado com sucesso")
	@PostMapping
	public ResponseEntity<OrderPostResponseDTO> insert(@Valid @RequestBody OrderDTO dto) {
		Order savedOrder = orderService.insert(dto);
		OrderPostResponseDTO responseDTO = new OrderPostResponseDTO(savedOrder);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
	}

	@Operation(summary = "Deleta um pedido pelo ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Pedido deletado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado") })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		orderService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Atualiza o status de um pedido")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado") })
	@PutMapping(value = "/{id}/status")
	public ResponseEntity<OrderResponseDTO> updateStatus(@PathVariable Long id, @RequestBody OrderStatusUpdateDTO dto) {
		Order updated = orderService.updateStatus(id, dto);
		return ResponseEntity.ok().body(new OrderResponseDTO(updated));
	}
}
