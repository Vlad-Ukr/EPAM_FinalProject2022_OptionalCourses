package com.example.optionalcoursesfp.service.impl;


import com.example.optionalcoursesfp.util.connection.ConnectionPool;
import com.example.optionalcoursesfp.entity.User;
import com.example.optionalcoursesfp.exeption.DatabaseException;
import com.example.optionalcoursesfp.repository.UserRepository;
import com.example.optionalcoursesfp.service.UserService;
import com.example.optionalcoursesfp.util.transaction.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ConnectionPool connectionPool;
    private final TransactionManager transactionManager;

    public UserServiceImpl(UserRepository userRepository, ConnectionPool connectionPool, TransactionManager transactionManager) {
        this.userRepository = userRepository;
        this.connectionPool = connectionPool;
        this.transactionManager = transactionManager;
    }

    public User getUserByLoginAndPassword(String login, String password) throws DatabaseException {
        User user;
        try (Connection con = connectionPool.getConnection()) {
            user = userRepository.getUserByLoginAndPassword(login, password, con);
        } catch (SQLException throwables) {
            throw new DatabaseException();
        }
        return user;
    }

    public void insertUser(String login, String password, int userRoleNumber) throws DatabaseException {
        try (Connection con = connectionPool.getConnection()) {
            userRepository.insertUser(login, password, userRoleNumber, con);
        } catch (SQLException throwables) {
            throw new DatabaseException();
        }
    }

    public int isTheUserAlready(String login) throws DatabaseException {
        int isTheUserAlreadyOutput;
        try (Connection con = connectionPool.getConnection()) {
            isTheUserAlreadyOutput = userRepository.isTheUserAlready(login, con);
        } catch (SQLException throwables) {
            throw new DatabaseException();
        }
        return isTheUserAlreadyOutput;
    }
}
