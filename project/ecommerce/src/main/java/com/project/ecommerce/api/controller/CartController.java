package com.project.ecommerce.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.ecommerce.model.CartItem;
import com.project.ecommerce.model.ShoppingCart;
import com.project.ecommerce.service.ShoppingCartService;
import com.project.ecommerce.model.dao.ShoppingCartRepository;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin("http://localhost:3000")
public class CartController {
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	@PostMapping("/addToCart/{iduser}")
	// id product
	public ResponseEntity<Object> addToCart(HttpServletRequest request, @RequestParam("id") int id,
			@PathVariable("iduser") Long iduser,
			@RequestParam("quantity") int quantity) {

		String sessionToken = (String) request.getSession(true).getAttribute("sessiontToken");
		ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(iduser);
		if (shoppingCart == null) {
			sessionToken = UUID.randomUUID().toString();
			request.getSession().setAttribute("sessiontToken", sessionToken);
			shoppingCartService.addShoppingCartFirstTime(id, iduser, sessionToken, quantity);
		} else {
			shoppingCartService.addToExistingShoppingCart(id, iduser, sessionToken, quantity);
		}
		return new ResponseEntity<>(" successsfully", HttpStatus.OK);

	}

	@GetMapping("/shoppingCart")
	public ResponseEntity<Object> showShoopingCartView(HttpServletRequest request) {
		List<CartItem> list = shoppingCartService.getitems();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/total_price/{iduser}")
	public ResponseEntity<Object>  View(@PathVariable("iduser") Long iduser) {

			String total = Double.toString(shoppingCartService.getPrice(iduser));
			return new ResponseEntity<>(total , HttpStatus.OK);
	}

	@PostMapping("/updateShoppingCart")
	// cart id
	public ResponseEntity<Object> updateCartItem(@RequestParam("item_id") Long id,
			@RequestParam("quantity") int quantity) {

		shoppingCartService.updateShoppingCartItem(id, quantity);
		return new ResponseEntity<>(" update successsfully", HttpStatus.OK);
	}

	@GetMapping("/removeCartItem/{id}")
	// cart id
	public ResponseEntity<Object> removeItem(@PathVariable("id") Long id, HttpServletRequest request) {
		shoppingCartService.removeCartIemFromShoppingCart(id);
		return new ResponseEntity<>(" successsfully", HttpStatus.OK);
	}

	@GetMapping("/clearShoppingCart/{iduser}")
	public ResponseEntity<Object> clearShoopiString(@PathVariable("iduser") Long iduser) {
			shoppingCartService.clearShoppingCart(iduser);
			return new ResponseEntity<>("clear", HttpStatus.OK);
	}

}
