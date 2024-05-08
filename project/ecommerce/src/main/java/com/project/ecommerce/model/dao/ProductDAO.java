package com.project.ecommerce.model.dao;

import com.project.ecommerce.model.Category;
import com.project.ecommerce.model.Product;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;


/**
 * Data Access Object for the LocalUser data.
 */
public interface ProductDAO extends CrudRepository<Product, Integer> {
   Optional<Product> findBynameIgnoreCase(String name);
   List<Product> findByCategory(Category category);
  
}
