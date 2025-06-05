package com.jmcurso.cource.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmcurso.cource.dto.CategoryDTO;
import com.jmcurso.cource.entities.Category;
import com.jmcurso.cource.repositories.CategoryRepository;
import com.jmcurso.cource.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public List<CategoryDTO> findAll() {
		List<Category> list = categoryRepository.findAll();
		return list.stream().map(CategoryDTO::new).toList();
	}

	public CategoryDTO findById(Long id) {
		Category entity = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		return new CategoryDTO(entity);
	}

	public Category insert(CategoryDTO dto) {
		Category entity = fromDTO(dto);
		return categoryRepository.save(entity);
	}

	public void delete(Long id) {
		if (!categoryRepository.existsById(id)) {
			throw new ResourceNotFoundException(id);
		}
		categoryRepository.deleteById(id);
	}

	public Category update(Long id, CategoryDTO dto) {
		Category entity = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		entity.setName(dto.getName());
		return categoryRepository.save(entity);
	}

	private Category fromDTO(CategoryDTO dto) {
		Category category = new Category();
		category.setName(dto.getName());
		return category;
	}
}
