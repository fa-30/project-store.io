package com.project.ecommerce.model.dao;

import com.project.ecommerce.model.admin;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Data Access Object for the LocalUser data.
 */
public interface LocaladminDAO extends CrudRepository<admin, Long> {

  Optional<admin> findByEmailIgnoreCase(String email);

}
