package com.example.optionalcoursesfp.service.impl;

import com.example.optionalcoursesfp.entity.FinishedCourse;
import com.example.optionalcoursesfp.util.connection.ConnectionPool;
import com.example.optionalcoursesfp.entity.Student;
import com.example.optionalcoursesfp.exeption.*;
import com.example.optionalcoursesfp.messages.Messages;
import com.example.optionalcoursesfp.repository.StudentRepository;
import com.example.optionalcoursesfp.repository.UserRepository;
import com.example.optionalcoursesfp.service.StudentService;
import com.example.optionalcoursesfp.util.transaction.TransactionManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ConnectionPool connectionPool;
    private final TransactionManager transactionManager;
    private static final Logger log = Logger.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(UserRepository userRepository, StudentRepository studentRepository, ConnectionPool connectionPool, TransactionManager transactionManager) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.connectionPool = connectionPool;
        this.transactionManager = transactionManager;
    }

    @Override
    public void insertStudent(String login, String password, String fullName) throws SQLQueryException {
        try (Connection con = connectionPool.getConnection()) {
            studentRepository.insertStudent(login, password, fullName, con, userRepository);
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
    }

    @Override
    public void setStudentMarkAndCourseIdNull(Student student, int courseNumber) throws SQLQueryException {
        try(Connection connection =connectionPool.getConnection()) {
           transactionManager.doTransaction(connection,connection1 -> studentRepository.setStudentCourseNull(student,courseNumber, connection1));
        } catch (SQLException throwables) {
            throw new SQLQueryException(throwables.getMessage(),throwables);
        }
    }
    @Override
    public void grateStudent(Student student, int courseNumber, int mark) throws SQLQueryException {
        try (Connection connection = connectionPool.getConnection()) {
            studentRepository.updateStudentMark(student, courseNumber, mark, connection);
        } catch (SQLQueryException throwables) {
            throw new SQLQueryException(throwables.getMessage(),throwables);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAllStudent() throws SQLQueryException {
        try (Connection con = connectionPool.getConnection()) {
            return studentRepository.getAllStudent(con);
        } catch (DatabaseException exception) {
            exception.printStackTrace();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public void blockStudent(String login) throws SQLQueryException {
        try (Connection connection = connectionPool.getConnection()) {
            studentRepository.blockStudent(login, connection);
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
    }

    @Override
    public void unBlockStudent(String login) throws SQLQueryException {
        try (Connection connection = connectionPool.getConnection()) {
            studentRepository.unBlockStudent(login, connection);
        }  catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
    }

    @Override
    public Student getStudentByLogin(String login) throws SQLQueryException {
        try (Connection connection = connectionPool.getConnection()) {
            return studentRepository.getStudentByLogin(login, connection);
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void registerStudentOnCourse(Student student, int courseId) throws SQLQueryException, StudentAlreadyRegisteredException, MaxAmountOfRegistrationException {
        log.info(student);
        try (Connection connection = connectionPool.getConnection()) {
            transactionManager.doTransaction(connection, con -> {
                    studentRepository.registerStudentOnCourse(student, courseId, connection);
            });
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(),e);
        } catch (StudentAlreadyRegisteredException e) {
            throw new StudentAlreadyRegisteredException();
        } catch (MaxAmountOfRegistrationException e) {
            throw new MaxAmountOfRegistrationException();
        }
    }

    @Override
    public List<Student> getStudentsByCourseId(int courseId) throws SQLQueryException {
        try (Connection connection = connectionPool.getConnection()) {
            return studentRepository.getStudentsByCourseId(courseId, connection);
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return Collections.emptyList();
    }
    @Override
    public List<FinishedCourse> getStudentsFromFinishedCourses( int courseId) throws SQLQueryException {
        try (Connection connection = connectionPool.getConnection()) {
            return studentRepository.getStudentsFromFinishedCourses(courseId,connection);
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return Collections.emptyList();
    }
}
