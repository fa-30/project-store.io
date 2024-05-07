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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

//import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	@PostMapping("/add/{id}")
	public ResponseEntity<Object> createPRO(@Valid @RequestBody Product product,HttpServletRequest request,@PathVariable("id")  int catid)
	{
	    try{
			String sessionToken = (String) request.getSession(true).getAttribute("loginadmin");
			if(sessionToken !=null){
		product = productService.createProduct(product,catid);
		return new ResponseEntity<>("Product is created successfully with id = " +product.getId(), HttpStatus.CREATED);}
		else{
			return null;
		}
		
	}
		catch (ProductAlreadyExistsException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();}
}

	@PutMapping("/Products/{id}/{catid}")
	public ResponseEntity<Object> updateProduct(@PathVariable("id") int id,@PathVariable("catid") int catid,
			@RequestBody Product product) 
	{
		boolean isProductExist = productService.isProductExist(id);
		if (isProductExist)
		{
			product.setId(id);
			productService.updateProduct(product,catid);
			return new ResponseEntity<>("Product is updated successsfully", HttpStatus.OK);
		}else{
		return null;}
	}

	@GetMapping("/searchProducts/{id}")
	public ResponseEntity<Object> getProduct(@PathVariable("id") int id)
	{
		boolean isProductExist = productService.isProductExist(id);
		if (isProductExist)
		{
			Product product = productService.getProduct(id);
			return new ResponseEntity<>(product, HttpStatus.OK);
		}else{
			return null;}

	}

	@GetMapping("/Products")
	public ResponseEntity<Object> getProducts()
	{
		List<Product> productList = productService.getProducts();
		return new ResponseEntity<>(productList, HttpStatus.OK);
	}

	@DeleteMapping("/Productsdelet/{id}")
	public ResponseEntity<Object> deleteProduct(@PathVariable("id") int id)
	{
		boolean isProductExist = productService.isProductExist(id);
		if (isProductExist)
		{
			productService.deleteProduct(id);
			return new ResponseEntity<>("Product is deleted successsfully", HttpStatus.OK);
		}
		else{
			return null;}

	}
	
	
}
