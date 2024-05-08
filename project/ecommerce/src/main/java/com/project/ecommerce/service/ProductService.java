package com.project.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ecommerce.exception.CategoryAlreadyExistsException;
import com.project.ecommerce.exception.ProductAlreadyExistsException;
import com.project.ecommerce.model.Category;
//import com.project.ecommerce.model.Category;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.dao.CategoryDAO;
import com.project.ecommerce.model.dao.ProductDAO;

@Service
public class ProductService {
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private CategoryDAO categoryDAO;

	public Product createProduct(Product product, String catName) throws ProductAlreadyExistsException {
		if (productDAO.findBynameIgnoreCase(product.getName()).isPresent()) {
			throw new ProductAlreadyExistsException();
		}
		Category cat = this.categoryDAO.findBynameIgnoreCase(catName)
				.orElseThrow(() -> new CategoryAlreadyExistsException("This Category name can't be found"));
		product.setCategory(cat);
		cat.getProduct().add(product);
		return productDAO.save(product);

	}

	public void updateProduct(Product product, int catid) {
		Category cat = this.categoryDAO.findById(catid)
				.orElseThrow(() -> new CategoryAlreadyExistsException("This Category id not found Catgory"));
		product.setCategory(cat);
		productDAO.save(product);
	}

	public Product getProduct(int id) {
		Optional<Product> optional = productDAO.findById(id);
		Product Product = optional.get();
		return Product;
	}

	public List<Product> getProducts() {
		return (List<Product>) productDAO.findAll();
	}

	public void deleteProduct(int id) {

		productDAO.deleteById(id);
	}

	public boolean isProductExist(int id) {
		return productDAO.existsById(id);
	}

}