package com.schoolcatalog.app.repositories;

import com.schoolcatalog.app.models.Account;
import com.schoolcatalog.app.models.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * StudentRepository
 */
@Repository
public interface StudentRepository extends MongoRepository<Student, String> {  
  Student findByAccount(Account account);
}