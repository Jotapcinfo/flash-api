package com.jmcurso.cource.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.jmcurso.cource.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductDTO {

	@NotBlank(message = "Nome é obrigatório")
	private String name;

	@NotBlank(message = "Descrição é obrigatória")
	private String description;

	@NotNull(message = "Preço é obrigatório")
	@Positive(message = "Preço deve ser positivo")
	private Double price;

	private String imgUrl;

	@NotNull(message = "Estoque é obrigatório")
	private Integer quantityInStock;

	@NotEmpty(message = "É necessário pelo menos uma categoria")
	private List<Long> categoryIds;

	public ProductDTO() {
	}

	public ProductDTO(String name, String description, Double price, String imgUrl, Integer quantityInStock, List<Long> categoryIds) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		this.quantityInStock = quantityInStock;
		this.categoryIds = categoryIds;
	}

	public ProductDTO(Product product) {
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.imgUrl = product.getImgUrl();
		this.quantityInStock = product.getQuantityInStock();
		this.categoryIds = product.getCategories().stream()
			.map(category -> category.getId())
			.collect(Collectors.toList());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getQuantityInStock() {
		return quantityInStock;
	}

	public void setQuantityInStock(Integer quantityInStock) {
		this.quantityInStock = quantityInStock;
	}

	public List<Long> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<Long> categoryIds) {
		this.categoryIds = categoryIds;
	}
}
