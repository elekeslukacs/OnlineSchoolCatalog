package com.schoolcatalog.app.repositories;

import com.schoolcatalog.app.utils.Role;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * RoleRepository
 */
@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
  Role findByRole(String role);
}