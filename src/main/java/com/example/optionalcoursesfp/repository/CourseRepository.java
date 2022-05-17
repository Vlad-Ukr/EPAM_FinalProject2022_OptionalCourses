package com.example.optionalcoursesfp.repository;

import com.example.optionalcoursesfp.entity.Course;
import com.example.optionalcoursesfp.entity.FinishedCourse;
import com.example.optionalcoursesfp.entity.Student;
import com.example.optionalcoursesfp.exeption.CourseAlreadyExistException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;

import java.sql.Connection;
import java.util.List;

public interface CourseRepository {
    List<Course> getAllCourses(Connection connection) throws SQLQueryException;
     Course getCourseById(int courseId,Connection connection) throws SQLQueryException;
    void addCourse(String name,int duration,int maxAmountOfStudent,String topic,int teacherId,Connection connection) throws SQLQueryException, CourseAlreadyExistException;
    void updateCourse(int id,String name,int duration,int maxAmountOfStudent,String topic,Connection connection) throws SQLQueryException, CourseAlreadyExistException;
    void  deleteCourse(int id,Connection connection) throws SQLQueryException;
    List<Course> getCoursesByTopicAndTeacher(int teacherId,String topic,Connection connection) throws  SQLQueryException;
    List<Course> getCoursesByTopic(String topic,Connection connection) throws  SQLQueryException;
    List<Course> getCoursesByTeacher(int teacherId,Connection connection) throws  SQLQueryException;
   List<Course> getStudentRegisteredCourses(Student student,Connection connection) throws SQLQueryException;
   void startCourse(int  courseId,Connection connection) throws SQLQueryException;
    void endCourse(int  courseId,Connection connection) throws SQLQueryException;
    void addFinishedCourse(String name, String topic,String teacherFullName,int duration,int mark,int studentId,int courseId,Connection connection) throws SQLQueryException;
    List<FinishedCourse> getStudentFinishedCourses(int studentId, Connection connection) throws SQLQueryException;
}
