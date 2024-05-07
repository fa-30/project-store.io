package com.project.ecommerce.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


/**
 * User for authentication with our website.
 */
@Entity
@Table(name = "user")
public class LocalUser {

  /** Unique id for the user. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  /** The username of the user. */
  @Column(name = "username", nullable = false, unique = true)
  private String username;
  /** The encrypted password of the user. */
  @Column(name = "password", nullable = false, length = 1000)
  private String password;
   /** The encrypted password of the user. */
   @Column(name = "confirmpassword", nullable = false, length = 1000)
   private String password2;

  /** The email of the user. */
  @Column(name = "email", nullable = false, unique = true, length = 320)
  private String email;
  /** The first name of the user. */
  @Column(name = "first_name", nullable = false)
  private String firstName;
  /** The last name of the user. */
  @Column(name = "last_name", nullable = false)
  private String lastName;


  /**
   * Gets the last name.
   * @return The last name.
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the last name.
   * @param lastName The last name.
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets the first name.
   * @return The first name.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the first name.
   * @param firstName The first name.
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets the email.
   * @return The email.
   */
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

  /**
   * Gets the username.
   * @return The username.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username.
   * @param username The username.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets the id.
   * @return The id.
   */
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

  public String getPassword2() {
    return password2;
  }

  public void setPassword2(String password2) {
    this.password2 = password2;
  }
}