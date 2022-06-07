package com.example.optionalcoursesfp.service.impl;


import com.example.optionalcoursesfp.dto.UserDTO;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.util.connection.ConnectionPool;
import com.example.optionalcoursesfp.entity.User;
import com.example.optionalcoursesfp.exeption.DatabaseException;
import com.example.optionalcoursesfp.repository.UserRepository;
import com.example.optionalcoursesfp.service.UserService;
import com.example.optionalcoursesfp.util.transaction.TransactionManager;
import com.example.optionalcoursesfp.validator.RegFormValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ConnectionPool connectionPool;

    public UserServiceImpl(UserRepository userRepository, ConnectionPool connectionPool) {
        this.userRepository = userRepository;
        this.connectionPool = connectionPool;

    }

    public User getUserByLoginAndPassword(String login, String password) throws SQLQueryException {
        User user;
        try (Connection con = connectionPool.getConnection()) {
            user = userRepository.getUserByLoginAndPassword(login, password, con);
        } catch (SQLException throwables) {
            throw new SQLQueryException(throwables.getMessage(),throwables);
        }
        return user;
    }

    public void insertUser(String login, String password, int userRoleNumber, String userPhoneNumber) throws SQLQueryException {
        try (Connection con = connectionPool.getConnection()) {
            userRepository.insertUser(login, password, userRoleNumber, userPhoneNumber, con);
        } catch (SQLException throwables) {
            throw new SQLQueryException(throwables.getMessage(),throwables);
        }
    }
    public Map<String, String> insertUser(UserDTO bean) throws SQLQueryException {
        RegFormValidator regFormValidator=new RegFormValidator();
        Map<String, String> errors=new HashMap<>();
        errors=regFormValidator.validate(bean);
        if(errors.size()!=0){
            return errors;
        }
        try (Connection con = connectionPool.getConnection()) {
            userRepository.insertUser(bean.getLogin(), bean.getPassword(), bean.getRole().ordinal()+1, bean.getPhoneNumber(), con);
        } catch (SQLException throwables) {
            throw new SQLQueryException(throwables.getMessage(),throwables);
        }
        return Collections.emptyMap();
    }

    public int isTheUserAlready(String login, String phoneNumber) throws SQLQueryException {
        int isTheUserAlreadyOutput;
        try (Connection con = connectionPool.getConnection()) {
            isTheUserAlreadyOutput = userRepository.isTheUserAlready(login, phoneNumber, con);
        } catch (SQLException throwables) {
            throw new SQLQueryException(throwables.getMessage(),throwables);
        }
        return isTheUserAlreadyOutput;
    }

    @Override
    public void changeAvatar(String login, String avatarName) throws SQLQueryException {
      try(Connection connection = connectionPool.getConnection()) {
          userRepository.changeAvatar(login,avatarName,connection);
      } catch (SQLException throwables) {
          throw new SQLQueryException(throwables.getMessage(),throwables);
      }
    }

}
