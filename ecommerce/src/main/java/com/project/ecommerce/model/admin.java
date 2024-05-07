package com.project.ecommerce.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;



@Entity
@Table(name = "admin")
public class admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
 
    @Column(name = "password", nullable = false, length = 1000)
    private String password;

    @Column(name = "email", nullable = false, unique = true, length = 320)
    private String email;
   
     
    public String getEmail() {
      return email;
    }
  
    /**
     * Sets the email.
     * @param email The email.
     */
    public void setEmail(String email) {
      this.email = email;
    }
  
    /**
     * Gets the encrypted password.
     * @return The password.
     */
    public String getPassword() {
      return password;
    }
  
    /**
     * Sets the password, this should be pre-encrypted.
     * @param password The password.
     */
    public void setPassword(String password) {
      this.password = password;
    }
  
    public Long getId() {
      return id;
    }
  
    /**
     * Sets the id.
     * @param id The id.
     */
    public void setId(Long id) {
      this.id = id;
    }
  
  
}

