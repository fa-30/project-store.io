package com.project.ecommerce.api.controller.auth;

import com.project.ecommerce.api.model.LoginBodyADMIN;
import com.project.ecommerce.api.model.LoginResponse;
import com.project.ecommerce.api.model.RegistrationADMIN;
import com.project.ecommerce.exception.UserAlreadyExistsException;
import com.project.ecommerce.service.AdminService;

import jakarta.validation.Valid;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller for handling authentication requests.
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:3000")
public class AuthenticationadminController {

  private AdminService adminService;
  /**
   * Spring injected constructor.
   * @param userService
   */
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
  public ResponseEntity<LoginResponse> loginadmin(@Valid @RequestBody LoginBodyADMIN loginBody) {
    String jwt = adminService.loginadmin(loginBody);
    if (jwt == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } else {
      LoginResponse response = new LoginResponse();
      response.setJwt(jwt);
      return ResponseEntity.ok(response);
    }
  }



}
