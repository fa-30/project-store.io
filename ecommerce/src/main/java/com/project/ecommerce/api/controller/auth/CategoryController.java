package com.project.ecommerce.api.controller.auth;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import com.project.ecommerce.api.model.CategoryBody;
import com.project.ecommerce.exception.CategoryAlreadyExistsException;
import com.project.ecommerce.model.Category;

import com.project.ecommerce.service.CategoryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
@RestController
public class CategoryController {
    @Autowired
	private CategoryService categoryService;
	@PostMapping("/addCAT")
	public ResponseEntity<Object> createPRO(@Valid @RequestBody Category category,HttpServletRequest request)
	{
	    try{
			String sessionToken = (String) request.getSession(true).getAttribute("loginadmin");
			if(sessionToken !=null){
		category = categoryService.create(category);
		return new ResponseEntity<>("Category is created successfully " , HttpStatus.CREATED);}
		else{
			return null;
		}
		
	}
		catch (CategoryAlreadyExistsException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();}
}

	@GetMapping("/searchCategorys/{id}")
	public ResponseEntity<Object> getCategory(@PathVariable("id") int id)
	{
		boolean isCategoryExist = categoryService.isCategoryExist(id);
		if (isCategoryExist)
		{
			Category category = categoryService.getCategory(id);
			return new ResponseEntity<>(category, HttpStatus.OK);
		}else{
			return null;}

	}

	@GetMapping("/Categorys")
	public ResponseEntity<Object> getCategorys()
	{
		List<Category> categoryList = categoryService.getCategorys();
		return new ResponseEntity<>(categoryList, HttpStatus.OK);
	}

	@DeleteMapping("/Categorysdelet/{id}")
	public ResponseEntity<Object> deleteCategory(@PathVariable("id") int id)
	{
		boolean isCategoryExist = categoryService.isCategoryExist(id);
		if (isCategoryExist)
		{
			categoryService.deleteCategory(id);
			return new ResponseEntity<>("Category is deleted successsfully", HttpStatus.OK);
		}
		else{
			return null;}

	}
	
	
}
