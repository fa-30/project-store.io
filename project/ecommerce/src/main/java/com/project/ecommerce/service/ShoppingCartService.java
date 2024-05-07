package com.project.ecommerce.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ecommerce.model.dao.CartItemRepository;
import com.project.ecommerce.model.dao.ShoppingCartRepository;
import com.project.ecommerce.model.CartItem;
import com.project.ecommerce.model.Product;
import com.project.ecommerce.model.ShoppingCart;

@Service
public class ShoppingCartService {

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	@Autowired
	private ProductService productService;
	@Autowired 
	private CartItemRepository cartItemRepository;
		
	public ShoppingCart addShoppingCartFirstTime(int id, String sessionToken, int quantity) {
		ShoppingCart shoppingCart = new ShoppingCart();
		CartItem cartItem = new CartItem();
		cartItem.setQuantity(quantity);
		cartItem.setDate(new Date());
		cartItem.setProduct(productService.getProduct(id));
		shoppingCart.getItems().add(cartItem);
		shoppingCart.setSessionToken(sessionToken);
		shoppingCart.setDate(new Date());
		return shoppingCartRepository.save(shoppingCart);

	}
  
	public ShoppingCart addToExistingShoppingCart(int id, String sessionToken, int quantity) {

		ShoppingCart shoppingCart = shoppingCartRepository.findBySessionToken(sessionToken);
		Product p = productService.getProduct(id);
		Boolean productDoesExistInTheCart = false;
		if (shoppingCart != null) {
		    Set<CartItem> items = shoppingCart.getItems();
			for (CartItem item : items) {
				if (item.getProduct().equals(p)) {
					productDoesExistInTheCart = true;
					item.setQuantity(item.getQuantity() + quantity);
					shoppingCart.setItems(items);
					return shoppingCartRepository.saveAndFlush(shoppingCart);  
				}
				
			}
		}
		if(!productDoesExistInTheCart && (shoppingCart != null))
		{
			CartItem cartItem1 = new CartItem();
			cartItem1.setDate(new Date());
			cartItem1.setQuantity(quantity);
			cartItem1.setProduct(p);
			shoppingCart.getItems().add(cartItem1);
			return shoppingCartRepository.saveAndFlush(shoppingCart);
		}
		
		return this.addShoppingCartFirstTime(id, sessionToken, quantity);

	}

	public ShoppingCart getShoppingCartBySessionTokent(String sessionToken) {
		
		return  shoppingCartRepository.findBySessionToken(sessionToken);
	}
	public List<CartItem> getitems()
	{
		return (List<CartItem>)cartItemRepository.findAll();
	}
	public  Double get(String sessionToken)
	{
		ShoppingCart shoppingCart = shoppingCartRepository.findBySessionToken(sessionToken);
		return shoppingCart.getTotalPrice();
	}

	public CartItem updateShoppingCartItem(Long id, int quantity) {
		CartItem cartItem = cartItemRepository.findById(id).get();
		cartItem.setQuantity(quantity);
		return cartItemRepository.saveAndFlush(cartItem);
		
	}

	public ShoppingCart removeCartIemFromShoppingCart(Long id, String sessionToken) {
		ShoppingCart shoppingCart = shoppingCartRepository.findBySessionToken(sessionToken);
		Set<CartItem> items = shoppingCart.getItems();
		CartItem cartItem = null;
		for(CartItem item : items) {
			if(item.getId()==id) {
				cartItem = item;
			}
		}
		items.remove(cartItem);
		cartItemRepository.delete(cartItem);
	    shoppingCart.setItems(items);
	    return shoppingCartRepository.save(shoppingCart);
	}

	public void clearShoppingCart(String sessionToken) {
		ShoppingCart sh = shoppingCartRepository.findBySessionToken(sessionToken);
		shoppingCartRepository.delete(sh);
		
	}

}
