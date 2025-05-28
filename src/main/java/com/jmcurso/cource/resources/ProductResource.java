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

import com.jmcurso.cource.dto.ProductDTO;
import com.jmcurso.cource.dto.ProductResponseDTO;
import com.jmcurso.cource.entities.Product;
import com.jmcurso.cource.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductResource {

	@Autowired
	private ProductService service;

	@Operation(summary = "Lista todos os produtos")
	@ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
	@GetMapping
	public ResponseEntity<List<ProductResponseDTO>> findAll() {
		List<Product> list = service.findAll();
		List<ProductResponseDTO> dtos = list.stream().map(ProductResponseDTO::new).toList();
		return ResponseEntity.ok().body(dtos);
	}

	@Operation(summary = "Busca um produto por ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Produto encontrado"),
			@ApiResponse(responseCode = "404", description = "Produto não encontrado") })
	@GetMapping("/{id}")
	public ResponseEntity<ProductResponseDTO> findById(
			@Parameter(description = "ID do produto", example = "1") @PathVariable Long id) {
		Product product = service.findById(id);
		return ResponseEntity.ok().body(new ProductResponseDTO(product));
	}

	@Operation(summary = "Cadastra um novo produto")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
			@ApiResponse(responseCode = "400", description = "Dados inválidos") })
	@PostMapping
	public ResponseEntity<ProductResponseDTO> insert(@Valid @RequestBody ProductDTO dto) {
		Product saved = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();
		return ResponseEntity.created(uri).body(new ProductResponseDTO(saved));
	}

	@Operation(summary = "Atualiza um produto existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Produto não encontrado") })
	@PutMapping("/{id}")
	public ResponseEntity<ProductResponseDTO> update(
			@Parameter(description = "ID do produto", example = "1") @PathVariable @Valid Long id,
			@RequestBody ProductDTO dto) {
		Product updated = service.update(id, dto);
		return ResponseEntity.ok().body(new ProductResponseDTO(updated));
	}

	@Operation(summary = "Remove um produto por ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
			@ApiResponse(responseCode = "404", description = "Produto não encontrado") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@Parameter(description = "ID do produto", example = "1") @PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
