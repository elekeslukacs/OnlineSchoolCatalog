package com.schoolcatalog.app.repositories;

import com.schoolcatalog.app.utils.Subject;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * SubjectRepository
 */
@Repository
public interface SubjectRepository extends MongoRepository<Subject, String> {
  Subject findByName(String subject);
}