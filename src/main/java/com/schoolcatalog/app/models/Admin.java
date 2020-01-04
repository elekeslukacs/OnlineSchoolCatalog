package com.schoolcatalog.app.models;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Admin
 */
@Document(collection = "admins")
public class Admin extends Person {
  //CRUD operations for each type of user

  public Account createAccount() {
    
    return null;
  }

  public Admin createAdmin() {
    return null;
  }
  
  public Teacher createTeacher() {

    return null;
  }

  public Student createStudent() {

    return null;
  }
}