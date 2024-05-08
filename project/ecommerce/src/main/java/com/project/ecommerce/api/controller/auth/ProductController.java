package com.project.ecommerce.api.controller.auth;

//import java.util.Map;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import com.project.ecommerce.api.model.ProductBody;
import com.project.ecommerce.exception.ProductAlreadyExistsException;
import com.project.ecommerce.model.Product;

import com.project.ecommerce.service.ProductService;

import jakarta.validation.Valid;

//import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
@CrossOrigin("http://localhost:3000")
public class ProductController {
	@Autowired
	private ProductService productService;

	@PostMapping("/add/{name}")
	public ResponseEntity<Object> createPRO(@PathVariable("name") String category, @Valid @RequestBody Product product) {
		try {

			product = productService.createProduct(product, category);
			return new ResponseEntity<>("Product is created successfully with id = " + product.getId(), HttpStatus.CREATED);
		}

		catch (ProductAlreadyExistsException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@PutMapping("/Products/{id}/{catid}")
	public ResponseEntity<Object> updateProduct(@PathVariable("id") int id, @PathVariable("catid") int catid,
			@RequestBody Product product) {
		boolean isProductExist = productService.isProductExist(id);
		if (isProductExist) {
			product.setId(id);
			productService.updateProduct(product, catid);
			return new ResponseEntity<>("Product is updated successsfully", HttpStatus.OK);
		} else {
			return null;
		}
	}

	@GetMapping("/searchProducts/{id}")
	public ResponseEntity<Object> getProduct(@PathVariable("id") int id) {
		boolean isProductExist = productService.isProductExist(id);
		if (isProductExist) {
			Product product = productService.getProduct(id);
			return new ResponseEntity<>(product, HttpStatus.OK);
		} else {
			return null;
		}

	}

	@GetMapping("/Products")
	public ResponseEntity<Object> getProducts() {
		List<Product> productList = productService.getProducts();
		return new ResponseEntity<>(productList, HttpStatus.OK);
	}

	@DeleteMapping("/Productsdelet/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable("id") int id) {
		boolean isProductExist = productService.isProductExist(id);
		if (isProductExist) {
			productService.deleteProduct(id);
			return new ResponseEntity<>("Product is deleted successsfully", HttpStatus.OK);
		} else {
			return null;
		}

	}

}
