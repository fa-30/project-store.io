package com.project.ecommerce.api.controller;

import java.util.List;
import java.util.UUID;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.project.ecommerce.model.CartItem;
import com.project.ecommerce.service.ShoppingCartService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CartController {
	@Autowired
	private ShoppingCartService shoppingCartService;

	@PostMapping("/addToCart")
	public ResponseEntity<Object> addToCart(HttpServletRequest request, @RequestParam("id") int id,
			@RequestParam("quantity") int quantity) {

		String sessionToken = (String) request.getSession(true).getAttribute("sessiontToken");
		if (sessionToken == null) {

			sessionToken = UUID.randomUUID().toString();
			request.getSession().setAttribute("sessiontToken", sessionToken);
			shoppingCartService.addShoppingCartFirstTime(id, sessionToken, quantity);
		} else {
			shoppingCartService.addToExistingShoppingCart(id, sessionToken, quantity);
		}
		return new ResponseEntity<>(" successsfully", HttpStatus.OK);
	
	}


	

	@GetMapping("/shoppingCart")
	public ResponseEntity<Object>  showShoopingCartView(HttpServletRequest request) {
		List<CartItem> list = shoppingCartService.getitems();
        return new ResponseEntity<>(list , HttpStatus.OK);
	}
	@GetMapping("/total price")
	public ResponseEntity<Object>  View(HttpServletRequest request) {
		String sessionToken = (String) request.getSession(true).getAttribute("sessiontToken");
		String total="total price :" + shoppingCartService.get(sessionToken);
        return new ResponseEntity<>(total , HttpStatus.OK);
	}



	@PostMapping("/updateShoppingCart")
	public ResponseEntity<Object> updateCartItem(@RequestParam("item_id") Long id,
			@RequestParam("quantity") int quantity) {
		
		shoppingCartService.updateShoppingCartItem(id,quantity);
		return new ResponseEntity<>(" update successsfully", HttpStatus.OK);
	}


	@GetMapping("/removeCartItem/{id}")
	public ResponseEntity<Object> removeItem(@PathVariable("id") Long id, HttpServletRequest request) {
		String sessionToken = (String) request.getSession(false).getAttribute("sessiontToken");
		System.out.println("got here ");
		shoppingCartService.removeCartIemFromShoppingCart(id,sessionToken);
		return new ResponseEntity<>(" successsfully", HttpStatus.OK);
	}
	


	@GetMapping("/clearShoppingCart")
	public ResponseEntity<Object> clearShoopiString(HttpServletRequest request) {
		String sessionToken = (String) request.getSession(false).getAttribute("sessiontToken");
		request.getSession(false).removeAttribute("sessiontToken");
		shoppingCartService.clearShoppingCart(sessionToken);
		return new ResponseEntity<>("clear", HttpStatus.OK);
	}
	
}







