package com.project.ecommerce.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.project.ecommerce.model.OrderUser;

public interface OrderDAO extends JpaRepository<OrderUser, Long>{
    List<OrderUser> findByUserId(Long userId);
}
