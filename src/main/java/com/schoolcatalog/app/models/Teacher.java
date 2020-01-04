package com.schoolcatalog.app.models;

import com.schoolcatalog.app.utils.Subject;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Teacher
 */
@Document(collection = "teachers")
public class Teacher extends Person {
  private Subject subject;

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }
  
}