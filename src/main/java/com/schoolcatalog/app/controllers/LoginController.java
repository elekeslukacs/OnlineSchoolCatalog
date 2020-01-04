package com.schoolcatalog.app.controllers;

import com.schoolcatalog.app.models.Account;
import com.schoolcatalog.app.models.Admin;
import com.schoolcatalog.app.models.Student;
import com.schoolcatalog.app.models.Teacher;
import com.schoolcatalog.app.repositories.AccountRepository;
import com.schoolcatalog.app.repositories.AdminRepository;
import com.schoolcatalog.app.repositories.RoleRepository;
import com.schoolcatalog.app.repositories.StudentRepository;
import com.schoolcatalog.app.repositories.TeacherRepository;
import com.schoolcatalog.app.utils.SessionInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * LoginController
 */
@Controller
public class LoginController {

  @Autowired
  AdminRepository adminRepo;

  @Autowired
  TeacherRepository teacherRepo;

  @Autowired
  StudentRepository studentRepo;

  @Autowired
  AccountRepository accountRepo;

  @Autowired
  RoleRepository roleRepo;

  @Autowired
  SessionInfo session;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ModelAndView login(@RequestParam("username") String username, @RequestParam("password") String password) {

    // check if account exists and if exists what type it is
    Account account = accountRepo.findByUsername(username);
    if (account == null) {
      System.err.println("This account does not exist");
      return null;
    }

    if (!account.getPassword().equals(password)) {
      System.err.println("Incorrect password");
      return null;
    }

    ModelAndView modelAndView = new ModelAndView();
    switch (account.getRole().getRole()) {
    case "ADMIN":
      Admin connectedAdmin = adminRepo.findByAccount(account);
      modelAndView.setViewName("redirect:/admin");
      session.setUser(connectedAdmin);
      break;

    case "TEACHER":
      Teacher connectedTeacher = teacherRepo.findByAccount(account);
      modelAndView.setViewName("redirect:/teacher");
      session.setUser(connectedTeacher);
      break;

    case "STUDENT":
      Student connectedStudent = studentRepo.findByAccount(account);
      modelAndView.setViewName("redirect:/student");
      session.setUser(connectedStudent);
      break;

    default:
      break;
    }

    return modelAndView;
  }

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public ModelAndView login() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("login");
    return modelAndView;
  }

  @RequestMapping(value = "/admin", method = RequestMethod.GET)
  public ModelAndView showAdminPage() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("admin");
    return modelAndView;
  }

//  @RequestMapping(value = "/teacher", method = RequestMethod.GET)
//  public ModelAndView showTeacherPage() {
//    ModelAndView modelAndView = new ModelAndView();
//    modelAndView.setViewName("teacher");
//    return modelAndView;
//  }

//  @RequestMapping(value = "/student", method = RequestMethod.GET)
//  public ModelAndView showStudentPage() {
//    ModelAndView modelAndView = new ModelAndView();
//    modelAndView.setViewName("student");
//    return modelAndView;
//  }

  @RequestMapping(value = "/test", method = RequestMethod.GET)
  public void test() {
    Account newAcc = new Account();
    newAcc.setUsername("admin");
    newAcc.setPassword("admin");
    newAcc.setRole(roleRepo.findByRole("ADMIN"));
    accountRepo.save(newAcc);

    Admin admin = new Admin();
    admin.setName("elekes");
    admin.setEmail("elekes@gmial.com");
    admin.setAccount(newAcc);
    adminRepo.save(admin);
  }

}