package com.example.optionalcoursesfp.service;


import com.example.optionalcoursesfp.entity.User;
import com.example.optionalcoursesfp.exeption.DatabaseException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;

import java.sql.Connection;

public interface UserService {
    User getUserByLoginAndPassword(String userLogin, String userPassword) throws DatabaseException;
    void  insertUser(String login, String password, int userRoleNumber) throws SQLQueryException, DatabaseException;
    int isTheUserAlready(String user_login) throws DatabaseException;
}
