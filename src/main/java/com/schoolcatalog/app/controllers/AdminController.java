package com.schoolcatalog.app.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.schoolcatalog.app.models.Account;
import com.schoolcatalog.app.models.Admin;
import com.schoolcatalog.app.models.Student;
import com.schoolcatalog.app.models.Teacher;
import com.schoolcatalog.app.repositories.AccountRepository;
import com.schoolcatalog.app.repositories.AdminRepository;
import com.schoolcatalog.app.repositories.RoleRepository;
import com.schoolcatalog.app.repositories.StudentRepository;
import com.schoolcatalog.app.repositories.SubjectRepository;
import com.schoolcatalog.app.repositories.TeacherRepository;
import com.schoolcatalog.app.utils.Role;
import com.schoolcatalog.app.utils.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * AdminController
 */
@RestController
public class AdminController {

  @Autowired
  AccountRepository accountRepo;

  @Autowired
  RoleRepository roleRepo;

  @Autowired
  SubjectRepository subjectRepo;

  @Autowired
  StudentRepository studentRepo;

  @Autowired
  TeacherRepository teacherRepo;

  @Autowired
  AdminRepository adminRepo;

  /****************************************
   ****************************************
   **************************************** 
   * VIEW
   ****************************************
   ****************************************
   ****************************************/

  @RequestMapping(value = "/showadminpage")
  public ModelAndView showAdminPage() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("redirect:/admin");
    return modelAndView;
  }

  @RequestMapping(value = "admins")
  public ModelAndView showAdmins() {
    return new ModelAndView("redirect:/admin/admins");
  }

  @RequestMapping(value = "/admin/admins")
  public ModelAndView admins() {
    ModelAndView modelAndView = new ModelAndView();

    // retrieve all admins from database
    List<Admin> admins = adminRepo.findAll();

    modelAndView.setViewName("staff");
    modelAndView.addObject("role", "ADMIN");
    modelAndView.addObject("persons", admins);
    return modelAndView;
  }

  @RequestMapping(value = "teachers")
  public ModelAndView showTeachers() {
    return new ModelAndView("redirect:/admin/teachers");
  }

  @RequestMapping(value = "/admin/teachers")
  public ModelAndView teachers() {
    ModelAndView modelAndView = new ModelAndView();

    // retrieve all teachers from database
    List<Teacher> teachers = teacherRepo.findAll();

    modelAndView.setViewName("staff");
    modelAndView.addObject("role", "TEACHER");
    modelAndView.addObject("persons", teachers);
    return modelAndView;
  }

  @RequestMapping(value = "students")
  public ModelAndView showStudents() {
    return new ModelAndView("redirect:/admin/students");
  }

  @RequestMapping(value = "/admin/students")
  public ModelAndView students() {
    ModelAndView modelAndView = new ModelAndView();

    // retrieve all students from database
    List<Student> students = studentRepo.findAll();

    modelAndView.setViewName("staff");
    modelAndView.addObject("role", "STUDENT");
    modelAndView.addObject("persons", students);
    return modelAndView;
  }

  /****************************************
   ****************************************
   **************************************** 
   * CREATE
   ****************************************
   ****************************************
   ****************************************/

  @RequestMapping(value = "/addPerson")
  public ModelAndView addPerson(@RequestParam String role) {
    switch (role) {
    case "ADMIN":
      return new ModelAndView("redirect:/admin/admins/add" + role);

    case "TEACHER":
      return new ModelAndView("redirect:/admin/teachers/add" + role);

    default:
      return new ModelAndView("redirect:/admin/students/add" + role);
    }
  }

  @RequestMapping(value = "/admin/admins/addADMIN")
  public ModelAndView addAdmin() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("addstaff");
    modelAndView.addObject("role", "ADMIN");
    return modelAndView;
  }

  @RequestMapping(value = "/admin/teachers/addTEACHER")
  public ModelAndView addTeacher() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("addstaff");
    modelAndView.addObject("role", "TEACHER");
    modelAndView.addObject("subjects", subjectRepo.findAll());
    return modelAndView;
  }

  @RequestMapping(value = "/admin/students/addSTUDENT")
  public ModelAndView addStudent() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("addstaff");
    modelAndView.addObject("role", "STUDENT");
    return modelAndView;
  }

  @RequestMapping(value = { "/admin/admins/save", "/admin/teachers/save", "/admin/students/save" })
  public ModelAndView savePerson(@RequestParam String role, @RequestParam String name, @RequestParam String email,
      @RequestParam String username, @RequestParam String password, @RequestParam String subject) {

    String target = "";
    switch (role) {
    case "ADMIN":
      target = "/admin/admins";
      Account adminAccount = createAccount(username, password, roleRepo.findByRole(role));
      accountRepo.save(adminAccount);
      Admin admin = new Admin();
      admin.setName(name);
      admin.setEmail(email);
      admin.setAccount(adminAccount);
      adminRepo.save(admin);
      break;

    case "TEACHER":
      target = "/admin/teachers";
      Account teacherAccount = createAccount(username, password, roleRepo.findByRole(role));
      accountRepo.save(teacherAccount);
      Teacher teacher = new Teacher();
      teacher.setName(name);
      teacher.setEmail(email);
      teacher.setAccount(teacherAccount);
      teacher.setSubject(subjectRepo.findByName(subject));
      teacherRepo.save(teacher);
      break;

    case "STUDENT":
      target = "/admin/students";
      Account studentAccount = createAccount(username, password, roleRepo.findByRole(role));
      accountRepo.save(studentAccount);
      Student student = new Student();
      student.setName(name);
      student.setEmail(email);
      student.setAccount(studentAccount);

      HashMap<String, ArrayList<Integer>> hm = new HashMap<>();
      ArrayList<Subject> subj = (ArrayList<Subject>) subjectRepo.findAll();
      for (Subject s : subj) {
        ArrayList<Integer> g = new ArrayList<>();
        g.add(1);
        g.add(1);
        g.add(1);
        g.add(1);
        g.add(1);
        hm.put(s.getName(), g);
      }

      student.setGrades(hm);
      studentRepo.save(student);
      break;

    default:
      break;
    }

    return new ModelAndView("redirect:" + target);
  }

  /****************************************
   ****************************************
   **************************************** 
   * EDIT
   ****************************************
   ****************************************
   ****************************************/

  @RequestMapping(value = "admin/edit/{id}")
  public ModelAndView editPerson(@RequestParam String role, @PathVariable String id) {
    ModelAndView modelAndView = new ModelAndView();
    switch (role) {
    case "ADMIN":
      modelAndView.setViewName("redirect:/admin/admins/edit/" + id);
      break;

    case "TEACHER":
      modelAndView.setViewName("redirect:/admin/teachers/edit/" + id);
      break;

    case "STUDENT":
      modelAndView.setViewName("redirect:/admin/students/edit/" + id);
      break;

    default:
      break;
    }

    return modelAndView;
  }

  @RequestMapping(value = "/admin/admins/edit/{id}")
  public ModelAndView editAdmin(@PathVariable String id) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("person", adminRepo.findById(id).get());
    modelAndView.addObject("role", "ADMIN");
    modelAndView.setViewName("editstaff");
    return modelAndView;
  }

  @RequestMapping(value = "/admin/teachers/edit/{id}")
  public ModelAndView editTeacher(@PathVariable String id) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("person", teacherRepo.findById(id).get());
    modelAndView.addObject("role", "TEACHER");
    modelAndView.addObject("subjects", subjectRepo.findAll());
    modelAndView.setViewName("editstaff");
    return modelAndView;
  }

  @RequestMapping(value = "/admin/students/edit/{id}")
  public ModelAndView editStudent(@PathVariable String id) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("person", studentRepo.findById(id).get());
    modelAndView.addObject("role", "STUDENT");
    modelAndView.setViewName("editstaff");
    return modelAndView;
  }

  @RequestMapping(value = "/admin/admins/edit/update/{id}")
  public ModelAndView updateAdmin(@PathVariable String id, @RequestParam String role, @RequestParam String name,
      @RequestParam String email, @RequestParam String username, @RequestParam String password,
      @RequestParam String subject) {

    Account currAccount = accountRepo.findById(adminRepo.findById(id).get().getAccount().getId()).get();
    Account newAccount = editAccount(username, password, currAccount);
    accountRepo.save(newAccount);
    Admin curr = adminRepo.findById(id).get();
    curr.setName(name);
    curr.setEmail(email);
    curr.setAccount(currAccount);
    adminRepo.save(curr);

    return new ModelAndView("redirect:/admin/admins");
  }

  @RequestMapping(value = "/admin/teachers/edit/update/{id}")
  public ModelAndView updateTeacher(@PathVariable String id, @RequestParam String role, @RequestParam String name,
      @RequestParam String email, @RequestParam String username, @RequestParam String password,
      @RequestParam String subject) {

    Account currAccount = accountRepo.findById(teacherRepo.findById(id).get().getAccount().getId()).get();
    Account newAccount = editAccount(username, password, currAccount);
    accountRepo.save(newAccount);
    Teacher curr = teacherRepo.findById(id).get();
    curr.setName(name);
    curr.setEmail(email);
    curr.setSubject(subjectRepo.findByName(subject));
    curr.setAccount(currAccount);
    teacherRepo.save(curr);

    return new ModelAndView("redirect:/admin/teachers");
  }

  @RequestMapping(value = "/admin/students/edit/update/{id}")
  public ModelAndView updateStudent(@PathVariable String id, @RequestParam String role, @RequestParam String name,
      @RequestParam String email, @RequestParam String username, @RequestParam String password,
      @RequestParam String subject) {

    Account currAccount = accountRepo.findById(studentRepo.findById(id).get().getAccount().getId()).get();
    Account newAccount = editAccount(username, password, currAccount);
    accountRepo.save(newAccount);
    Student curr = studentRepo.findById(id).get();
    curr.setName(name);
    curr.setEmail(email);
    curr.setAccount(currAccount);
    studentRepo.save(curr);

    return new ModelAndView("redirect:/admin/students");
  }

  /****************************************
   ****************************************
   **************************************** 
   * DELETE
   ****************************************
   ****************************************
   ****************************************/

  @RequestMapping(value = "admin/delete/{id}")
  public ModelAndView deletePerson(@RequestParam String role, @PathVariable String id) {
    ModelAndView modelAndView = new ModelAndView();
    switch (role) {
    case "ADMIN":
      modelAndView.setViewName("redirect:/admin/admins/delete/" + id);
      break;

    case "TEACHER":
      modelAndView.setViewName("redirect:/admin/teachers/delete/" + id);
      break;

    case "STUDENT":
      modelAndView.setViewName("redirect:/admin/students/delete/" + id);
      break;

    default:
      break;
    }

    return modelAndView;
  }

  @RequestMapping(value = "/admin/admins/delete/{id}")
  public ModelAndView deleteAdmin(@PathVariable String id) {
    accountRepo.deleteById(adminRepo.findById(id).get().getAccount().getId());
    adminRepo.deleteById(id);
    return new ModelAndView("redirect:/admin/admins");
  }

  @RequestMapping(value = "/admin/teachers/delete/{id}")
  public ModelAndView deleteTeacher(@PathVariable String id) {
    accountRepo.deleteById(teacherRepo.findById(id).get().getAccount().getId());
    teacherRepo.deleteById(id);
    return new ModelAndView("redirect:/admin/teachers");
  }

  @RequestMapping(value = "/admin/students/delete/{id}")
  public ModelAndView deleteStudent(@PathVariable String id) {
    accountRepo.deleteById(studentRepo.findById(id).get().getAccount().getId());
    studentRepo.deleteById(id);
    return new ModelAndView("redirect:/admin/students");
  }


  /****************************************
   ****************************************
   **************************************** 
   * UTILS
   ****************************************
   ****************************************
   ****************************************/

  private Account createAccount(String username, String password, Role role) {
    Account account = new Account();
    account.setUsername(username);
    account.setPassword(password);
    account.setRole(role);
    return account;
  }

  private Account editAccount(String username, String password, Account currAccount) {
    currAccount.setUsername(username);
    currAccount.setPassword(password);
    return currAccount;
  }
}