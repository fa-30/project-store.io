package com.project.ecommerce.service;

import com.project.ecommerce.api.model.LoginBodyADMIN;
import com.project.ecommerce.api.model.RegistrationADMIN;
import com.project.ecommerce.exception.UserAlreadyExistsException;
import com.project.ecommerce.model.admin;
import com.project.ecommerce.model.dao.LocaladminDAO;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service for handling user actions.
 */
@Service
public class AdminService {
  private LocaladminDAO localadminDAO;
  private EncryptionService encryptionService;
  private JWTService jwtService;


  public AdminService(LocaladminDAO localadminDAO, EncryptionService encryptionService, JWTService jwtService) {
    this.localadminDAO = localadminDAO;
    this.encryptionService = encryptionService;
    this.jwtService = jwtService;
  }

  /**
   * Attempts to register a user given the information provided.
   * @param registrationBody The registration information.
   * @return The local user that has been written to the database.
   * @throws UserAlreadyExistsException Thrown if there is already a user with the given information.
   */
  public admin registeradmin(RegistrationADMIN registrationBody) throws UserAlreadyExistsException {
    if (localadminDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()) {
      throw new UserAlreadyExistsException("admin aleady exists change  email");
    }
    admin admin = new admin();
    admin.setEmail(registrationBody.getEmail());
    admin.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
    return localadminDAO.save(admin);
  }

  /**
   * Logins in a user and provides an authentication token back.
   * @param loginBody The login request.
   * @return The authentication token. Null if the request was invalid.
   */
  public String loginadmin(LoginBodyADMIN loginBody) {
    Optional<admin> opUser = localadminDAO.findByEmailIgnoreCase(loginBody.getEmail());
    if (opUser.isPresent()) {
      admin admin = opUser.get();
      if (encryptionService.verifyPassword(loginBody.getPassword(), admin.getPassword())) {
        return jwtService.generateeJWT(admin);
      }
    }
    return null;
  }



}
