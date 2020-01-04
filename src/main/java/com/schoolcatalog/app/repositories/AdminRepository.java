package com.schoolcatalog.app.repositories;

import com.schoolcatalog.app.models.Account;
import com.schoolcatalog.app.models.Admin;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * AdminRepository
 */
@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {  
  Admin findByAccount(Account account);
}