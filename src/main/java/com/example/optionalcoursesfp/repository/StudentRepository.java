package com.example.optionalcoursesfp.repository;

import com.example.optionalcoursesfp.entity.FinishedCourse;
import com.example.optionalcoursesfp.entity.Student;
import com.example.optionalcoursesfp.exeption.MaxAmountOfRegistrationException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.exeption.StudentAlreadyRegisteredException;

import java.sql.Connection;
import java.util.List;

public interface StudentRepository {

     void insertStudent(String login, String password, String fullName,Connection connection,UserRepository userRepository) throws SQLQueryException;
     void setStudentCourseNull(Student student, int courseNumber, Connection connection) throws SQLQueryException;
    List<Student> getAllStudent(Connection connection) throws SQLQueryException;
    void blockStudent(String login,Connection connection) throws SQLQueryException;
    void unBlockStudent(String login,Connection connection) throws SQLQueryException;
    Student getStudentByLogin(String login,Connection connection) throws SQLQueryException;
    void registerStudentOnCourse(Student student, int courseId,Connection connection) throws SQLQueryException, StudentAlreadyRegisteredException, MaxAmountOfRegistrationException;
    List<Student> getStudentsByCourseId(int courseId,Connection connection) throws  SQLQueryException;
    void updateStudentMark(Student student,int courseNumber,int mark,Connection connection) throws SQLQueryException;
    public List<FinishedCourse> getStudentsFromFinishedCourses(int courseId, Connection connection) throws SQLQueryException;
}
