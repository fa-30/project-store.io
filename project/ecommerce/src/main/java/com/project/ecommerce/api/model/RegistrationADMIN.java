package com.project.ecommerce.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * The information required to register a user.
 */
public class RegistrationADMIN {

  
  @NotNull(message = "shouldn't be null")
  @NotBlank
  @Email(message = "invalid email address")
  private String email;
  /** The password. */
  @NotNull(message = "shouldn't be null")
  @NotBlank
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
  @Size(min=6, max=32)
  private String password;
  

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }


}
