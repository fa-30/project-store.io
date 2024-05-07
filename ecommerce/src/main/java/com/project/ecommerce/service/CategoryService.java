package com.project.ecommerce.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ecommerce.exception.CategoryAlreadyExistsException;
import com.project.ecommerce.model.Category;
import com.project.ecommerce.model.dao.CategoryDAO;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
 
    @Autowired
    private CategoryDAO categoryDAO;
       
       public Category create(Category category) throws CategoryAlreadyExistsException
       {
           if (categoryDAO.findBynameIgnoreCase(category.getName()).isPresent()) {
               throw new CategoryAlreadyExistsException();
             }
           return categoryDAO.save(category);
        }
   
       
       
       public Category getCategory(int id)
       {
           Optional<Category> optional=categoryDAO.findById(id);
           Category category = optional.get();
           return category;
       }
   
   
       public List<Category> getCategorys()
       {
           return (List<Category>)categoryDAO.findAll();
       }
   
   
       public void deleteCategory(int id)
       {
           categoryDAO.deleteById(id);
       }
   
   
       public boolean isCategoryExist(int id)
       {
           return categoryDAO.existsById(id);
       }
      
}
