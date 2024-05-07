package com.project.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * A product available for purchasing.
 */
@Entity
@Table(name = "product")

public class Product {

  /** Unique id for the product. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private int id;
  /** The name of the product. */
  @Column(name = "name", nullable = false, unique = true)
  private String name;
  /** The long description of the product. */
  @Column(name = "long_description")
  private String longDescription;
  /** The price of the product. */
  @Column(name = "price", nullable = false)
  private Double price;
  private String imageName;
  @Column(name = "quantity", nullable = false)
  private Integer quantity;
	/*private String category;
  
  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }
*/
@ManyToOne(fetch = FetchType.EAGER)
	private Category category;
  
  public int getCategory() {
  return category.getCategoryId();
}


public void setCategory(Category category) {
  this.category = category;
}

  /**
   * Gets the quantity in stock.
   * @return The quantity.
   */
  public Integer getQuantity() {
    return quantity;
  }

  /**
   * Sets the quantity in stock of the product.
   * @param quantity The quantity to be set.
   */
  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  /**
   * Gets the price of the product.
   * @return The price.
   */
  public Double getPrice() {
    return price;
  }

  /**
   * Sets the price of the product.
   * @param price The price.
   */
  public void setPrice(Double price) {
    this.price = price;
  }

  /**
   * Gets the long description of the product.
   * @return The long description.
   */
  public String getLongDescription() {
    return longDescription;
  }

  /**
   * Sets the long description of the product.
   * @param longDescription The long description.
   */
  public void setLongDescription(String longDescription) {
    this.longDescription = longDescription;
  }

  public String getName() {
    return name;
  }

  /**
   * Sets the name of the product.
   * @param name The name.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the id of the product.
   * @return The id.
   */
  public int getId() {
    return id;
  }

  /**
   * Sets the id of the product.
   * @param id The id.
   */
  public void setId(int id) {
    this.id = id;
  }

  public String getImageName() {
    return imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  public void deleteById(Long id2) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
  }



}