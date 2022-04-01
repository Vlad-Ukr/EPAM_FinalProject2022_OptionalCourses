package com.example.optionalcoursesfp.repository.impl;

import com.example.optionalcoursesfp.entity.Teacher;
import com.example.optionalcoursesfp.entity.UserRole;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.exeption.UserAlreadyExistException;
import com.example.optionalcoursesfp.messages.Messages;
import com.example.optionalcoursesfp.repository.TeacherRepository;
import com.example.optionalcoursesfp.repository.UserRepository;
import org.apache.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepositoryImpl implements TeacherRepository {
    private static final String GET_ALL_TEACHERS="SELECT * from teacher";
    private static final String ADD_TEACHER="INSERT INTO teacher(login,full_name) VALUES (?,?)";
    private static final String GET_TEACHER_BY_LOGIN="SELECT * FROM teacher WHERE login=?";
    private static final Logger log = Logger.getLogger(TeacherRepositoryImpl.class);
    @Override
    public List<Teacher> getAllTeachers(Connection connection) throws SQLQueryException {
        List<Teacher> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_ALL_TEACHERS)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                result.add(new Teacher(resultSet.getInt("id"),resultSet.getString("login"),resultSet.getString("full_name")));
               log.info((resultSet.getInt("id")+" "+resultSet.getString("login")+" "+resultSet.getString("full_name")));
            }
        } catch (SQLException e) {
            throw new SQLQueryException(Messages.ERR_CANNOT_EXECUTE_QUERY, e);
        }
        return result;
    }

    @Override
    public void addTeacher(String login, String fullName,String password, Connection connection, UserRepository userRepository) throws SQLQueryException {
        try {
                try (PreparedStatement ps = connection.prepareStatement(ADD_TEACHER)) {
                    ps.setString(1, login);
                    ps.setString(2, fullName);
                    ps.executeUpdate();
                    log.info("teacher-" + login + " was added into database");
                } catch (SQLException e) {
                    throw new SQLQueryException(Messages.ERR_CANNOT_EXECUTE_QUERY, e);
                }
        } catch (SQLQueryException e) {
            throw new SQLQueryException(Messages.ERR_CANNOT_EXECUTE_QUERY, e);
        }
    }

    @Override
    public Teacher getTeacherByLogin(String login,Connection connection) throws SQLQueryException {
        Teacher teacher= new Teacher();
        try (PreparedStatement ps = connection.prepareStatement(GET_TEACHER_BY_LOGIN)) {
            ps.setString(1, login);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                log.info(resultSet.getInt("id"));
                teacher.setId(resultSet.getInt("id"));
                teacher.setLogin(resultSet.getString("login"));
                teacher.setFullName(resultSet.getString("full_name"));
            }
        } catch (SQLException e) {
            throw new SQLQueryException(Messages.ERR_CANNOT_EXECUTE_QUERY, e);
        }
        log.info(teacher);
        return teacher;
    }
}
