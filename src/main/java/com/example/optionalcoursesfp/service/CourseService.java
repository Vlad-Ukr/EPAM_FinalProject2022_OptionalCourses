package com.example.optionalcoursesfp.service;

import com.example.optionalcoursesfp.entity.Course;
import com.example.optionalcoursesfp.entity.FinishedCourse;
import com.example.optionalcoursesfp.entity.Student;
import com.example.optionalcoursesfp.entity.Teacher;
import com.example.optionalcoursesfp.exeption.CourseAlreadyExistException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;

import java.sql.Connection;
import java.util.List;

public interface CourseService {
    List<Course> getAllCourses() throws SQLQueryException;
    Course getCourseById(int courseId) throws SQLQueryException;
    void addCourse(String name,int duration,int maxAmountOfStudent,String topic,int teacherId) throws SQLQueryException, CourseAlreadyExistException;
    void updateCourse(int id,String name,int duration,int maxAmountOfStudent,String topic) throws SQLQueryException,CourseAlreadyExistException;
    void  deleteCourse(int id) throws SQLQueryException;
    List<Course> getCoursesByTopicAndTeacher(int teacherId, String topic) throws  SQLQueryException;
    List<Course> getCoursesByTopic(String topic) throws  SQLQueryException;
    List<Course> getCoursesByTeacher(int teacherId) throws  SQLQueryException;
    List<Course> getStudentRegisteredCourses(Student student) throws  SQLQueryException;
    void startCourse(int  courseId) throws SQLQueryException;
    void endCourse(int  courseId) throws SQLQueryException;
    void addFinishedCourse(String name, String topic,String teacherFullName,int duration,int mark,int studentId,int courseId) throws SQLQueryException;
    List<FinishedCourse> getStudentFinishedCourses(int studentId) throws SQLQueryException;
}
