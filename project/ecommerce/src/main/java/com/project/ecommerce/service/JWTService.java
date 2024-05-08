package com.project.ecommerce.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.project.ecommerce.model.LocalUser;
import com.project.ecommerce.model.admin;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service for handling JWTs for user authentication.
 */
@Service
public class JWTService {

  /** The secret key to encrypt the JWTs with. */
  @Value("${jwt.algorithm.key}")
  private String algorithmKey;
  /** The issuer the JWT is signed with. */
  @Value("${jwt.issuer}")
  private String issuer;
  /** How many seconds from generation should the JWT expire? */
  @Value("${jwt.expiryInSeconds}")
  private int expiryInSeconds;
  /** The algorithm generated post construction. */
  private Algorithm algorithm;
  /** The JWT claim key for the username. */

  /**
   * Post construction method.
   */
  @PostConstruct
  public void postConstruct() {
    algorithm = Algorithm.HMAC256(algorithmKey);
  }

  /**
   * Generates a JWT based on the given user.
   * 
   * @param user The user to generate for.
   * @return The JWT.
   */
  public String generateJWT(LocalUser user) {
    return JWT.create()
        .withClaim("id", user.getId())
        .withClaim("email", user.getEmail())
        .withClaim("role", "customer")
        .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds)))
        .withIssuer(issuer)
        .sign(algorithm);
  }

  public String generateeJWT(admin admin) {
    return JWT.create()
        .withClaim("id", admin.getId())
        .withClaim("email", admin.getEmail())
        .withClaim("role", "admin")
        .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds)))
        .withIssuer(issuer)
        .sign(algorithm);
  }
}
