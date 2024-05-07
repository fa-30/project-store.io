package com.project.ecommerce.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Category {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int categoryId;
private String name;
private String imageName;

public String getImageName() {
    return imageName;
}


public void setImageName(String imageName) {
    this.imageName = imageName;
}

@OneToMany(mappedBy = "category",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private Set<Product> product=new HashSet<>();

public Category(int categoryId, String name, String imageName) {
    super();
    this.categoryId = categoryId;
    this.name = name;
    this.imageName=imageName;
}


public Category() {
    super();
}


public int getCategoryId() {
    return categoryId;
}

public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public Set<Product> getProduct() {
    return product;
}

public void setProduct(Set<Product> product) {
    this.product = product;
}
	

}
