package com.schoolcatalog.app.repositories;

import com.schoolcatalog.app.models.Account;
import com.schoolcatalog.app.models.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * TeacherRepository
 */
@Repository
public interface TeacherRepository extends MongoRepository<Teacher, String> {  
  Teacher findByAccount(Account account);
}