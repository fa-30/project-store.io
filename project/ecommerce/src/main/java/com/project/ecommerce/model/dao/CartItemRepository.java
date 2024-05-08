package com.project.ecommerce.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.ecommerce.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

}
