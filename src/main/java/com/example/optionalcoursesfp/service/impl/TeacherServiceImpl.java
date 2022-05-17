package com.example.optionalcoursesfp.service.impl;

import com.example.optionalcoursesfp.util.connection.ConnectionPool;
import com.example.optionalcoursesfp.entity.Teacher;
import com.example.optionalcoursesfp.exeption.DatabaseException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.repository.TeacherRepository;
import com.example.optionalcoursesfp.repository.UserRepository;
import com.example.optionalcoursesfp.service.TeacherService;
import com.example.optionalcoursesfp.util.transaction.TransactionManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class TeacherServiceImpl implements TeacherService {
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final ConnectionPool connectionPool;

    public TeacherServiceImpl(UserRepository userRepository, TeacherRepository teacherRepository, ConnectionPool connectionPool) {
        this.userRepository = userRepository;
        this.teacherRepository = teacherRepository;
        this.connectionPool = connectionPool;
    }

    @Override
    public List<Teacher> getAllTeachers() throws SQLQueryException {
        try (Connection con = connectionPool.getConnection()){
            return teacherRepository.getAllTeachers(con);
        } catch (SQLException exception) {
            throw new SQLQueryException(exception.getMessage(),exception);
        }
    }

    @Override
    public void addTeacher(String login,String password,String fullName) throws SQLQueryException {
        try (Connection con = connectionPool.getConnection()) {
            teacherRepository.addTeacher(login,fullName,password,con,userRepository);
        } catch (SQLException throwables) {
            throw new SQLQueryException(throwables.getMessage(),throwables);
        }
    }

    @Override
    public Teacher getTeacherByLogin(String login) throws SQLQueryException {
        try (Connection con = connectionPool.getConnection()) {
            return teacherRepository.getTeacherByLogin(login,con);
        } catch (SQLException throwables) {
            throw new SQLQueryException(throwables.getMessage(),throwables);
        }
    }
}
