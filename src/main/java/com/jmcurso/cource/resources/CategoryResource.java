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

import com.jmcurso.cource.dto.CategoryDTO;
import com.jmcurso.cource.entities.Category;
import com.jmcurso.cource.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	@Autowired
	private CategoryService service;

	@Operation(summary = "Lista todas as categorias")
	@ApiResponse(responseCode = "200", description = "Categorias retornadas com sucesso")
	@GetMapping
	public ResponseEntity<List<CategoryDTO>> findAll() {
		List<CategoryDTO> dtoList = service.findAll();
		return ResponseEntity.ok().body(dtoList);
	}

	@Operation(summary = "Busca categoria por ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
			@ApiResponse(responseCode = "404", description = "Categoria não encontrada") })
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
		CategoryDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@Operation(summary = "Cria uma nova categoria")
	@ApiResponse(responseCode = "201", description = "Categoria criada com sucesso")
	@PostMapping
	public ResponseEntity<CategoryDTO> insert(@Valid @RequestBody CategoryDTO dto) {
		Category saved = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId()).toUri();
		return ResponseEntity.created(uri).body(new CategoryDTO(saved));
	}

	@Operation(summary = "Atualiza uma categoria existente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Categoria não encontrada") })
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO dto) {
		Category updated = service.update(id, dto);
		return ResponseEntity.ok().body(new CategoryDTO(updated));
	}

	@Operation(summary = "Remove uma categoria")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso"),
			@ApiResponse(responseCode = "404", description = "Categoria não encontrada") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
