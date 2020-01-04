package com.schoolcatalog.app.controllers;

import com.schoolcatalog.app.utils.Group;
import com.schoolcatalog.app.models.Student;
import com.schoolcatalog.app.models.Teacher;
import com.schoolcatalog.app.repositories.*;
import com.schoolcatalog.app.utils.SessionInfo;
import com.schoolcatalog.app.utils.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * TeacherController
 */
@RestController
public class TeacherController {
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

  @Autowired
  SessionInfo session;

  @RequestMapping(value = "/teacher")
  public ModelAndView showAll(){
    Teacher connected = (Teacher)session.getUser();
    ModelAndView modelAndView = new ModelAndView();
    List<Student> students = studentRepo.findAll();

    for(Student s : students){
      s.getGradesForSubject(connected.getSubject());
      System.out.println(s.getArrayOfGrades().toString());
      System.out.println(s.getName());
    }

    ArrayList<Integer> note = new ArrayList<>();
    note.add(5);
    note.add(9);
    modelAndView.setViewName("teacher");
    modelAndView.addObject("students", students);
    modelAndView.addObject("teacher", connected);
    //modelAndView.addObject("groups", connected.getGroups());
    //modelAndView.addObject("subject", connected.getSubject().getName());
    modelAndView.addObject("subject", connected.getSubject());
    modelAndView.addObject("note",  note);
    return modelAndView;
  }

  @RequestMapping(value = "/showStudents")
  public ModelAndView selectGroup(@RequestParam String group){
    return new ModelAndView("redirect:/teacher/" + group);
  }

  @RequestMapping(value = "/teacher/{group}")
  public ModelAndView showGroup(@PathVariable("group") String group){
    Teacher connected = (Teacher)session.getUser();
    ModelAndView modelAndView = new ModelAndView();
    List<Student> students = studentRepo.findAll();
    //trebuie find by group

    modelAndView.setViewName("teacher");
    modelAndView.addObject("students", students);
    // modelAndView.addObject("groups", connected.getGroups());
    return modelAndView;
  }

  @RequestMapping(value = "/done/{id}")
  public ModelAndView editGrade(@PathVariable("id") String student, @PathParam("nota1") int nota1, @PathParam("nota2") int nota2,
                                @PathParam("nota3") int nota3, @PathParam("nota4") int nota4, @PathParam("nota5") int nota5){
    System.out.println(student);
    Student stud = studentRepo.findById(student).get();
    Teacher current = (Teacher)session.getUser();
    Subject subj = current.getSubject();
    //  ArrayList<Integer> note = new ArrayList<>();
    if(stud.getGrades().containsKey(subj.getName())){
      stud.getGrades().get(subj.getName()).clear();
      stud.getGrades().get(subj.getName()).add(nota1);
      stud.getGrades().get(subj.getName()).add(nota2);
      stud.getGrades().get(subj.getName()).add(nota3);
      stud.getGrades().get(subj.getName()).add(nota4);
      stud.getGrades().get(subj.getName()).add(nota5);
    }
    else{
      ArrayList<Integer> note = new ArrayList<>();
      note.add(nota1);
      note.add(nota2);
      note.add(nota3);
      note.add(nota4);
      note.add(nota5);

      stud.getGrades().put(subj.getName(), note);
    }
    studentRepo.save(stud);
    return new ModelAndView("redirect:/teacher");
  }





}