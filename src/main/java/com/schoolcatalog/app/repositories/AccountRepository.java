package com.schoolcatalog.app.repositories;

import com.schoolcatalog.app.models.Account;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * AccountRepository
 */
@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
  Account findByUsername(String username);
}