package com.jmcurso.cource.dto;

import com.jmcurso.cource.entities.Category;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {
	private Long id;

	@NotBlank(message = "Nome da categoria é obrigatório")
	private String name;

	public CategoryDTO() {
	}

	public CategoryDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public CategoryDTO(Category category) {
		id = category.getId();
		name = category.getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}