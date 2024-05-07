package com.project.ecommerce.exception;

/**
 * Exception thrown at user registration if an existing user already exists
 * with the given information.
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
