package com.project.ecommerce.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ecommerce.model.Category;

public interface CategoryDAO extends JpaRepository<Category, Integer> {
    Optional<Category> findBynameIgnoreCase(String name);
}
