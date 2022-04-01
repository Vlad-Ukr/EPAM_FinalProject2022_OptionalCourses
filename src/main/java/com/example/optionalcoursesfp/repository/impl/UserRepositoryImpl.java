package com.example.optionalcoursesfp.repository.impl;

import com.example.optionalcoursesfp.entity.User;
import com.example.optionalcoursesfp.entity.UserRole;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.hashing.Hasher;
import com.example.optionalcoursesfp.messages.Messages;
import com.example.optionalcoursesfp.repository.UserRepository;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryImpl implements UserRepository {
    private static final String GET_USER_BY_LOGIN_AND_PASSWORD="SELECT * FROM users WHERE login=? AND password=?";
    private static final String INSET_USER="INSERT INTO users(login,password,role_id) VALUES (?,?,?)";
    private static final String IS_THE_USER_ALREADY="SELECT count(id) as count FROM users WHERE login=?";
    @Override
    public User getUserByLoginAndPassword(String login, String password, Connection connection) throws SQLQueryException {
        User user = new User();
        try (PreparedStatement ps = connection.prepareStatement(GET_USER_BY_LOGIN_AND_PASSWORD)) {
            ps.setString(1, login);
            ps.setString(2, String.valueOf(new Hasher().hashString(password)));
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(UserRole.determineUserRole(resultSet.getInt("role_id")));
            }
        } catch (SQLException e) {
            throw new SQLQueryException(Messages.ERR_CANNOT_EXECUTE_QUERY, e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void insertUser(String login, String password,int userRoleNumber,Connection connection) throws SQLQueryException {
        try(PreparedStatement ps = connection.prepareStatement(INSET_USER)){
            ps.setString(1,login);
            ps.setString(2, String.valueOf(new Hasher().hashString(password)));
            ps.setInt(3, userRoleNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLQueryException(Messages.ERR_CANNOT_EXECUTE_QUERY, e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
/*
  isTheUserAlready-return 0 if user isn`t int dataBase
  return 1 if user already is in dataBase
  return -1 if happened SQLException
 */
    @Override
    public int isTheUserAlready(String login,Connection connection)  throws SQLQueryException {
        try(PreparedStatement ps = connection.prepareStatement(IS_THE_USER_ALREADY)){
            ps.setString(1,login);
            ResultSet resultSet = ps.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            throw new SQLQueryException(Messages.ERR_CANNOT_EXECUTE_QUERY, e);
        }
        return -1;
    }
}
