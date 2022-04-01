package com.example.optionalcoursesfp.repository;

import com.example.optionalcoursesfp.entity.Teacher;
import com.example.optionalcoursesfp.exeption.SQLQueryException;

import java.sql.Connection;
import java.util.List;

public interface TeacherRepository {
    List<Teacher> getAllTeachers(Connection connection) throws SQLQueryException;
    void addTeacher(String login, String fullName,String password, Connection connection,UserRepository userRepository) throws SQLQueryException;
    Teacher getTeacherByLogin(String login,Connection connection) throws SQLQueryException;
}
