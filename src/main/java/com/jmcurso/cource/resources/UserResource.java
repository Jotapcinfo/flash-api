package com.jmcurso.cource.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jmcurso.cource.dto.UserDTO;
import com.jmcurso.cource.dto.UserOrderListWithTotalDTO;
import com.jmcurso.cource.dto.UserPasswordUpdateDTO;
import com.jmcurso.cource.dto.UserPasswordUpdatedResponseDTO;
import com.jmcurso.cource.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Usuários")
@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserService service;

	@Operation(summary = "Lista todos os usuários")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso") })
	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		List<UserDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@Operation(summary = "Busca um usuário por ID")
	@Parameter(name = "id", description = "ID do usuário", example = "1")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		UserDTO userDTO = service.findById(id);
		return ResponseEntity.ok(userDTO);
	}

	@Operation(summary = "Lista todos os pedidos de um usuário específico (Endpoint: /users/1/orders)")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Pedidos do usuário retornados com sucesso"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
	@GetMapping("/{id}/orders")
	public ResponseEntity<UserOrderListWithTotalDTO> getOrdersWithTotal(@PathVariable Long id) {
		UserOrderListWithTotalDTO dto = service.findOrdersWithTotal(id);
		return ResponseEntity.ok(dto);
	}

	@Operation(summary = "Cria um novo usuário")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos") })
	@PostMapping
	public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserDTO dto) {
		UserDTO createdDto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdDto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(createdDto);
	}

	@Operation(summary = "Remove um usuário por ID")
	@Parameter(name = "id", description = "ID do usuário", example = "1")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Atualiza dados de um usuário")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(
			@Parameter(description = "ID do usuário", example = "1") @PathVariable Long id,
			@RequestBody @Valid UserDTO dto) {
		UserDTO updatedDto = service.update(id, dto);
		return ResponseEntity.ok().body(updatedDto);
	}

	@Operation(summary = "Atualiza a senha de um usuário - (Endpoint: /users/1/password)")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso"),
			@ApiResponse(responseCode = "400", description = "Senha antiga inválida"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
	@PutMapping(value = "/{id}/password")
	public ResponseEntity<UserPasswordUpdatedResponseDTO> updatePassword(@PathVariable Long id,
			@RequestBody @Valid UserPasswordUpdateDTO dto) {

		UserPasswordUpdatedResponseDTO response = service.updatePassword(id, dto.getOldPassword(),
				dto.getNewPassword());
		return ResponseEntity.ok(response);
	}
}
