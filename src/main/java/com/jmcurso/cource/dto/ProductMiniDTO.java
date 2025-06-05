package com.jmcurso.cource.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.jmcurso.cource.entities.Category;
import com.jmcurso.cource.entities.Product;

public class ProductMiniDTO {

	private Long id;
	private String name;
	private String description;
	private Double price;
	private String imgUrl;
	private List<CategoryDTO> categories;

	public ProductMiniDTO(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.imgUrl = product.getImgUrl();
		this.categories = product.getCategories().stream().map(CategoryDTO::new).collect(Collectors.toList());
	}

	public static class CategoryDTO {
		private Long id;
		private String name;

		public CategoryDTO(Category category) {
			this.id = category.getId();
			this.name = category.getName();
		}

		public Long getId() {
			return id;
		}

		public String getName() {
			return name;
		}
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Double getPrice() {
		return price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}
}
