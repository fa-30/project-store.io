package com.project.ecommerce.api.controller.auth;

import com.project.ecommerce.api.model.LoginBodyADMIN;
import com.project.ecommerce.api.model.RegistrationADMIN;
import com.project.ecommerce.exception.UserAlreadyExistsException;
import com.project.ecommerce.service.AdminService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller for handling authentication requests.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationadminController {

  private AdminService adminService;

  public AuthenticationadminController(AdminService adminService) {
    this.adminService = adminService;
  }
 

  @SuppressWarnings("rawtypes")
  @PostMapping("/registeradmin")
  public ResponseEntity registeradmin(@Valid @RequestBody RegistrationADMIN registrationBody) {
    try {
      adminService.registeradmin(registrationBody);
      return ResponseEntity.ok().build();
    } catch (UserAlreadyExistsException ex) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }



  @PostMapping("/loginadmin")
  public ResponseEntity<Object> loginadmin(@Valid @RequestBody LoginBodyADMIN loginBody,HttpServletRequest request) {

    String seh = adminService.loginadmin(loginBody);
    if (seh == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } else {
      String login = UUID.randomUUID().toString();
      request.getSession().setAttribute("loginadmin", login);
      return new ResponseEntity<>("login successsfully", HttpStatus.OK);
    }
  }



}
