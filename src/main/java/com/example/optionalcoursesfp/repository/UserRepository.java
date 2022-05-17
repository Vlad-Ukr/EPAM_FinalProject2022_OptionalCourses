package com.example.optionalcoursesfp.repository;


import com.example.optionalcoursesfp.entity.User;
import com.example.optionalcoursesfp.exeption.DatabaseException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;

import java.sql.Connection;

public interface UserRepository {
     User getUserByLoginAndPassword(String login, String password, Connection connection) throws SQLQueryException;
     void  insertUser(String login,String password,int userRoleNumber,String userPhoneNumber,Connection connection) throws SQLQueryException;
     int isTheUserAlready(String login,String phoneNumber,Connection connection) throws SQLQueryException;
     void  changeAvatar(String login,String avatarName,Connection connection) throws SQLQueryException;
}
