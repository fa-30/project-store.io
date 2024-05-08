package com.project.ecommerce.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.ecommerce.model.OrderUser;
import com.project.ecommerce.model.ShoppingCart;
import com.project.ecommerce.model.dao.ShoppingCartRepository;
import com.project.ecommerce.service.OrderService;
import com.project.ecommerce.service.ShoppingCartService;


@RestController
@CrossOrigin("http://localhost:3000")
public class OrderController {
    @Autowired
    private  OrderService orderService;
    @Autowired
    private  ShoppingCartService shoppingCartService;


@Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @PostMapping("/addOrder/{id}")
    public ResponseEntity<Object> addorder( @PathVariable("id") Long id,
            @RequestBody OrderUser orderUser) {

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(id);
        if (shoppingCart != null) {
            orderService.creatorder( id, orderUser);
            shoppingCartService.clearShoppingCart(id);
            return new ResponseEntity<>("ORDER is created successfully " , HttpStatus.CREATED);}
       else{return new ResponseEntity<>( HttpStatus.BAD_REQUEST);}
    }

    @PostMapping("/addOrderbank/{id}")
    public ResponseEntity<Object> addorderr( @PathVariable("id") Long id,
            @RequestBody OrderUser orderUser) {

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(id);
        if (shoppingCart != null) {
            orderService.creatorderr( id, orderUser);
            shoppingCartService.clearShoppingCart(id);
            return new ResponseEntity<>("ORDER is created successfully " , HttpStatus.CREATED);}
       else{return new ResponseEntity<>( HttpStatus.BAD_REQUEST);}
    }    
    @GetMapping("/orders/user/{userId}")
    public List<OrderUser> getOrdersByUserId(@PathVariable("userId") Long userId) {
        return orderService.getOrdersByUserId(userId);
    }
}
