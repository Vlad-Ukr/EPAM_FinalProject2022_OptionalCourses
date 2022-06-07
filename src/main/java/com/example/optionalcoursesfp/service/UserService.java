package com.example.optionalcoursesfp.service;


import com.example.optionalcoursesfp.dto.UserDTO;
import com.example.optionalcoursesfp.entity.User;
import com.example.optionalcoursesfp.exeption.DatabaseException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.exeption.UserAlreadyExistException;

import java.util.Map;


public interface UserService {
    User getUserByLoginAndPassword(String userLogin, String userPassword) throws SQLQueryException;
    void  insertUser(String login, String password, int userRoleNumber,String userPhoneNumber) throws SQLQueryException, DatabaseException, UserAlreadyExistException;
     Map<String, String> insertUser(UserDTO bean) throws SQLQueryException, UserAlreadyExistException;
    int isTheUserAlready(String userLogin,String userPhoneNumber) throws SQLQueryException;
    void  changeAvatar(String login,String avatarName) throws SQLQueryException;
}
