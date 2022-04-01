package com.example.optionalcoursesfp.service;

import com.example.optionalcoursesfp.entity.Teacher;
import com.example.optionalcoursesfp.exeption.SQLQueryException;

import java.util.List;

public interface TeacherService {
    List<Teacher> getAllTeachers() throws SQLQueryException;
    void addTeacher(String login,String password,String fullName) throws SQLQueryException;
    Teacher getTeacherByLogin(String login) throws  SQLQueryException;
}
