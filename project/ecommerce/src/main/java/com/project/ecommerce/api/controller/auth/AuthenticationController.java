package com.project.ecommerce.api.controller.auth;

import com.project.ecommerce.api.model.LoginBody;
import com.project.ecommerce.api.model.LoginResponse;
import com.project.ecommerce.api.model.RegistrationBody;
import com.project.ecommerce.api.model.forgetpass;
import com.project.ecommerce.exception.UserAlreadyExistsException;
import com.project.ecommerce.model.LocalUser;
import com.project.ecommerce.service.UserService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class AuthenticationController {
  @Autowired
  private UserService userService;
  
  /**
   * Spring injected constructor.
   * @param userService
   */
  public AuthenticationController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Post Mapping to handle registering users.
   * @param registrationBody The registration information.
   * @return Response to front end.
   * @throws UserAlreadyExistsException 
   */
  @SuppressWarnings("rawtypes")
  
  @PostMapping("/register")
  public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody)  {
    try {
      LocalUser newUser = userService.registerUser(registrationBody);
      if (newUser != null) {
        return ResponseEntity.ok("Signedup successfully");
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }
    } catch (UserAlreadyExistsException ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * Post Mapping to handle user logins to provide authentication token.
   * @param loginBody The login information.
   * @return The authentication token if successful.
   */
  @PostMapping("/login")
  public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody) {
    String jwt = userService.loginUser(loginBody);
    if (jwt == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    } else {
      LoginResponse response = new LoginResponse();
      response.setJwt(jwt);
      return ResponseEntity.ok(response);
    }
  }

  @GetMapping("/users")
	public ResponseEntity<Object> getusers()
	{
		List<LocalUser> userList = userService.getusers();
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}

  @DeleteMapping("/userdelet/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id)
	{
		boolean isExist = userService.isExist(id);
		if (isExist)
		{
			userService.deleteuser(id);
			return new ResponseEntity<>("deleted successsfully", HttpStatus.OK);
		}
		else{
			return null;}

	}

  @PostMapping("/forget")
  public ResponseEntity<LoginResponse>forget(@Valid @RequestBody forgetpass loginBody) {
    
      userService.forgetpas(loginBody);
      return ResponseEntity.status(HttpStatus.OK).build();
  }
  @PostMapping("/code")
  public ResponseEntity<Object>code(@RequestBody Map <String, String> request) {
    
      String result = userService.code(request.get("code"));
      if (result != null && result.equals("done")) {
          return ResponseEntity.status(HttpStatus.OK).build();
      } else {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }
  
  }
 
  @PostMapping("/change")
  public ResponseEntity<Object> forgetpassword(@RequestBody forgetpass loginBody ){
    String ok=userService.forgetpassword(loginBody);
    if(ok!=null){
    return ResponseEntity.status(HttpStatus.OK).build();}
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }
}
