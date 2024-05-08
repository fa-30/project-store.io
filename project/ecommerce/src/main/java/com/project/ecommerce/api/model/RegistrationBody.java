package com.project.ecommerce.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * The information required to register a user.
 */
public class RegistrationBody {

  @NotNull(message = "shouldn't be null")
  @NotBlank
  @Size(min=3, max=255)
  private String username;
  /** The email. */
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
    /** The password. */
    @NotNull(message = "shouldn't be null")
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
    @Size(min=6, max=32)
    private String password2;
 
  /** The first name. */
  @NotNull(message = "shouldn't be null")
  @NotBlank
  private String firstName;
  /** The last name. */
  @NotNull(message = "shouldn't be null")
  @NotBlank
  private String lastName;


public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }
  public String getPassword2() {
    return password2;
  }

}
