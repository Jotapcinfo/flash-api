package com.jmcurso.cource.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmcurso.cource.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
