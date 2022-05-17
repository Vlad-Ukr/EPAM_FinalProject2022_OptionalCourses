package com.example.optionalcoursesfp.service;


import com.example.optionalcoursesfp.entity.FinishedCourse;
import com.example.optionalcoursesfp.entity.Student;
import com.example.optionalcoursesfp.exeption.MaxAmountOfRegistrationException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.exeption.StudentAlreadyRegisteredException;

import java.util.List;

public interface StudentService {
     void insertStudent(String login, String password, String fullName) throws SQLQueryException;
     void setStudentMarkAndCourseIdNull(Student student, int courseNumber) throws SQLQueryException;
     List<Student> getAllStudent() throws SQLQueryException;
     void blockStudent(String login) throws SQLQueryException;
     void unBlockStudent(String login) throws SQLQueryException;
     Student getStudentByLogin(String login) throws SQLQueryException;
     void registerStudentOnCourse(Student student, int courseId) throws SQLQueryException, StudentAlreadyRegisteredException, MaxAmountOfRegistrationException;
     List<Student> getStudentsByCourseId(int courseId) throws  SQLQueryException;
     void grateStudent(Student student, int courseNumber, int mark) throws SQLQueryException;
     public List<FinishedCourse> getStudentsFromFinishedCourses( int courseId) throws SQLQueryException;
}
