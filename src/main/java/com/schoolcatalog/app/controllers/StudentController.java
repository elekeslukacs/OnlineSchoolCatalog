package com.schoolcatalog.app.controllers;

import com.schoolcatalog.app.models.Person;
import com.schoolcatalog.app.models.Student;
import com.schoolcatalog.app.repositories.*;
import com.schoolcatalog.app.utils.SessionInfo;
import com.schoolcatalog.app.utils.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * StudentController
 */
@RestController
public class StudentController {

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
  private SessionInfo session;

  @RequestMapping(value = "/student")
  public ModelAndView showGrades()
  {
    ModelAndView modelAndView = new ModelAndView();

    List<Subject> subjects = subjectRepo.findAll();

    Student student = (Student)session.getUser();

    HashMap<String, ArrayList<Integer> > grades = student.getGrades();

    //hardcodat notele pana pune Lukacs notele ca profesor
//    for(int i=0; i<subjects.size(); i++){
//      Random rand = new Random();
//      ArrayList<Integer> studentGrades = new ArrayList<Integer>();
//      studentGrades.add(rand.nextInt(10));
//      studentGrades.add(rand.nextInt(10));
//      studentGrades.add(rand.nextInt(10));
//      grades.put(subjects.get(i), studentGrades);
//      //student.setGrades(grades);
//    }

    //System.out.println(grades);

    //   modelAndView.addObject("subjects", subjects);
    modelAndView.addObject("grades", grades);
    modelAndView.addObject("student", student);
    modelAndView.setViewName("student");
    return modelAndView;
  }

}