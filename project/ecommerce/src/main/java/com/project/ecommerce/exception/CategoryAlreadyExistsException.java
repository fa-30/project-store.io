package com.project.ecommerce.exception;

public class CategoryAlreadyExistsException extends RuntimeException{

    public CategoryAlreadyExistsException(){
        super();
    }
    public CategoryAlreadyExistsException(String massage){
        super(massage);
    }
}
