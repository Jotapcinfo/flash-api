package com.jmcurso.cource.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.jmcurso.cource.dto.ProductDTO;
import com.jmcurso.cource.entities.Category;
import com.jmcurso.cource.entities.Product;
import com.jmcurso.cource.repositories.CategoryRepository;
import com.jmcurso.cource.repositories.ProductRepository;
import com.jmcurso.cource.services.exceptions.DatabaseException;
import com.jmcurso.cource.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Product findById(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Product insert(ProductDTO dto) {
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		return productRepository.save(entity);
	}

	public Product update(Long id, ProductDTO dto) {
		Product entity = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		copyDtoToEntity(dto, entity);
		return productRepository.save(entity);
	}

	public void delete(Long id) {
		if (!productRepository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade: " + e.getMessage());
		}
	}

	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());
		entity.setQuantityInStock(dto.getQuantityInStock());

		entity.getCategories().clear();
		for (Long catId : dto.getCategoryIds()) {
			Category category = categoryRepository.findById(catId)
					.orElseThrow(() -> new ResourceNotFoundException(catId));
			entity.getCategories().add(category);
		}
	}
}
