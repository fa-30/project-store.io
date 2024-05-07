package com.project.ecommerce.service;

import com.project.ecommerce.api.model.LoginBody;
import com.project.ecommerce.api.model.RegistrationBody;
import com.project.ecommerce.api.model.forgetpass;
import com.project.ecommerce.exception.UserAlreadyExistsException;
import com.project.ecommerce.model.LocalUser;
import com.project.ecommerce.model.dao.LocalUserDAO;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for handling user actions.
 */
@Service
public class UserService {
  @Autowired
  private LocalUserDAO localUserDAO;
  private EncryptionService encryptionService;

  @Autowired
  private EmailService emailService;
  public String mycode;
  /**
   * Constructor injected by spring.
   *
   * @param localUserDAO
   * @param encryptionService
   */
  public UserService(LocalUserDAO localUserDAO, EncryptionService encryptionService) {
    this.localUserDAO = localUserDAO;
    this.encryptionService = encryptionService;
  }

  /**
   * Attempts to register a user given the information provided.
   * @param registrationBody The registration information.
   * @return The local user that has been written to the database.
   * @throws UserAlreadyExistsException Thrown if there is already a user with the given information.
   */
  public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
    if (localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()
            || localUserDAO.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()) {
      throw new UserAlreadyExistsException("user aleady exists change username or email");
    }else{
      LocalUser user = new LocalUser();
      user.setEmail(registrationBody.getEmail());
      user.setUsername(registrationBody.getUsername());
      user.setFirstName(registrationBody.getFirstName());
      user.setLastName(registrationBody.getLastName());
      user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
      user.setPassword2(encryptionService.encryptPassword(registrationBody.getPassword2()));
      if(registrationBody.getPassword().equals(registrationBody.getPassword2())){
        return localUserDAO.save(user);}
      else
        return null;}

  }

  /**
   * Logins in a user and provides an authentication token back.
   * @param loginBody The login request.
   * @return The authentication token. Null if the request was invalid.
   */
  public String loginUser(LoginBody loginBody) {
    Optional<LocalUser> opUser = localUserDAO.findByEmailIgnoreCase(loginBody.getEmail());
    if (opUser.isPresent()) {
      LocalUser user = opUser.get();
      if (encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())) {
        return "done";
      }
    }
    return null;
  }


  public String forgetpas(forgetpass loginBody){
    Optional<LocalUser> opUser = localUserDAO.findByEmailIgnoreCase(loginBody.getEmail());
    if (opUser.isPresent()) {
      mycode=String.valueOf(otpGenerator());
      sendMail(loginBody.getEmail(), loginBody.getEmail()+".com", " your code", mycode);
    }
    return null;
  }

  public String code(String otp){
    if(mycode.equals(otp)){
      return "done";
    }else return null;
  }



  public String forgetpassword(forgetpass loginBody){
    Optional<LocalUser> opUser = localUserDAO.findByEmailIgnoreCase(loginBody.getEmail());
    if (opUser.isPresent()) {
      LocalUser user = opUser.get();
      user.setPassword(encryptionService.encryptPassword( loginBody.getPassword()));
      user.setPassword2(encryptionService.encryptPassword( loginBody.getPassword2()));
      localUserDAO.save(user);
      return "done";
    }
    return null;
  }


  public String User(String sessionTokeny) {

    return null;
  }

  public List<LocalUser> getusers()
  {
    return (List<LocalUser>)localUserDAO.findAll();
  }


  public void deleteuser(Long id)
  {
    localUserDAO.deleteById(id);
  }


  public boolean isExist(Long id)
  {
    return localUserDAO.existsById(id);
  }

  public String sendMail( String to, String cc, String subject, String body) {
    return emailService.sendMail( to, cc, subject, body);
  }
  public Integer otpGenerator(){
    Random random=new Random();
    return random.nextInt(100_00,999_999);
  }


  public  LocalUser getuser(Long id)
  {
    Optional<LocalUser> optional = localUserDAO.findById(id);
    LocalUser localUser = optional.get();
    return localUser;
  }

}

