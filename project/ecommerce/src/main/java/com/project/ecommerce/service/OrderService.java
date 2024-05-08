package com.project.ecommerce.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ecommerce.model.LocalUser;
import com.project.ecommerce.model.OrderUser;
import com.project.ecommerce.model.ShoppingCart;
import com.project.ecommerce.model.dao.OrderDAO;
import com.project.ecommerce.model.dao.ShoppingCartRepository;

@Service
public class OrderService {
 
  @Autowired
  private OrderDAO orderDAO;
  @Autowired
	private UserService userService;
  @Autowired
	private ShoppingCartRepository shoppingCartRepository;
  @Autowired
  private EmailService emailService;

  public OrderUser creatorder(Long iduser,OrderUser orderUser) {
    OrderUser order =new OrderUser();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(iduser);
        order.setAdress(orderUser.getAdress());
    order.setCity(orderUser.getCity());
    order.setDate(new Date());

    LocalUser localUser=userService.getuser(iduser);
    order.setUser(localUser);
    order.setDiscripthion(orderUser.getDiscripthion());
    order.setTotalPrice(shoppingCart.getTotalPrice());
    order.setItemsNumber(shoppingCart.getItemsNumber());
    order.setPayment("cash");
    sendMail(localUser.getEmail(), localUser.getEmail()+".com", "Your order", "your order created sucssesfully you will pay cash thank you ");
    return orderDAO.save(order);

    }

  public OrderUser creatorderr(Long iduser,OrderUser orderUser) {
    OrderUser order =new OrderUser();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(iduser);
        order.setAdress(orderUser.getAdress());
    order.setCity(orderUser.getCity());
    order.setDate(new Date());
    LocalUser localUser=userService.getuser(iduser);
    order.setUser(localUser);
    order.setDiscripthion(orderUser.getDiscripthion());
    order.setTotalPrice(shoppingCart.getTotalPrice());
    order.setItemsNumber(shoppingCart.getItemsNumber());
    order.setPayment("card");
    sendMail(localUser.getEmail(), localUser.getEmail()+".com", "Your order", "your order created sucssesfully by your credietcard thank you ");
    return orderDAO.save(order);
    }

  public List<OrderUser> getOrdersByUserId(Long userId) {
    return orderDAO.findByUserId(userId);
  }

  public String sendMail( String to, String cc, String subject, String body) {
    return emailService.sendMail( to, cc, subject, body);
    }
  }
