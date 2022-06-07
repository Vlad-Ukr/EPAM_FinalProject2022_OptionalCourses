package com.example.optionalcoursesfp.service.impl;

import com.example.optionalcoursesfp.dto.CourseDTO;
import com.example.optionalcoursesfp.entity.FinishedCourse;
import com.example.optionalcoursesfp.util.connection.ConnectionPool;
import com.example.optionalcoursesfp.entity.Course;
import com.example.optionalcoursesfp.entity.Student;
import com.example.optionalcoursesfp.exeption.CourseAlreadyExistException;
import com.example.optionalcoursesfp.exeption.DatabaseException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.messages.Messages;
import com.example.optionalcoursesfp.repository.CourseRepository;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.util.transaction.TransactionManager;
import com.example.optionalcoursesfp.validator.CourseValidator;
import com.example.optionalcoursesfp.validator.RegFormValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseServiceImp implements CourseService {
    private final CourseRepository courseRepository;
    private final ConnectionPool connectionPool;
    private final TransactionManager transactionManager;

    public CourseServiceImp(CourseRepository courseRepository, ConnectionPool connectionPool, TransactionManager transactionManager) {
        this.courseRepository = courseRepository;
        this.connectionPool = connectionPool;
        this.transactionManager = transactionManager;
    }


    @Override
    public List<Course> getAllCourses() throws SQLQueryException {
        try (Connection con = connectionPool.getConnection()) {
            return courseRepository.getAllCourses(con);
        } catch (DatabaseException exception) {
            exception.printStackTrace();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public Course getCourseById(int courseId) throws SQLQueryException {
        try (Connection con = connectionPool.getConnection()) {
            return courseRepository.getCourseById(courseId, con);
        } catch (DatabaseException exception) {
            exception.printStackTrace();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void addCourse(String name, int duration, int maxAmountOfStudent, String topic, int teacherId) throws SQLQueryException, CourseAlreadyExistException {
        try (Connection con = connectionPool.getConnection()) {
            courseRepository.addCourse(name, duration, maxAmountOfStudent, topic, teacherId, con);
        } catch (CourseAlreadyExistException exception) {
            throw new CourseAlreadyExistException(Messages.COURSE_IS_ALREADY_EXISTS, exception);
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
    }
    @Override
    public Map<String, String> addCourse(CourseDTO bean) throws SQLQueryException, CourseAlreadyExistException {
        CourseValidator courseValidator=new CourseValidator();
        Map<String, String> errors=new HashMap<>();
        errors=courseValidator.validate(bean);
        if(errors.size()!=0){
            return errors;
        }
        try (Connection con = connectionPool.getConnection()) {
            courseRepository.addCourse(bean.getName(), bean.getDuration(), bean.getMaxAmountOfStudent(), bean.getTopic(), bean.getTeacherId(), con);
        } catch (CourseAlreadyExistException exception) {
            throw new CourseAlreadyExistException(Messages.COURSE_IS_ALREADY_EXISTS, exception);
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return Collections.emptyMap();
    }

    @Override
    public  Map<String, String> updateCourse(CourseDTO bean) throws SQLQueryException, CourseAlreadyExistException {
        CourseValidator courseValidator=new CourseValidator();
        Map<String, String> errors=new HashMap<>();
        errors=courseValidator.validate(bean);
        if(errors.size()!=0){
            return errors;
        }
        try (Connection con = connectionPool.getConnection()) {
            transactionManager.doTransaction(con, connection -> {
                try {
                    courseRepository.updateCourse(bean.getId(), bean.getName(), bean.getDuration(), bean.getMaxAmountOfStudent(), bean.getTopic(), connection);
                } catch (CourseAlreadyExistException e) {
                    throw new CourseAlreadyExistException(Messages.COURSE_IS_ALREADY_EXISTS, e);
                }
            });
        } catch (SQLException e) {
            throw new CourseAlreadyExistException(Messages.COURSE_IS_ALREADY_EXISTS, e);
        }
        return Collections.emptyMap();
    }
    @Override
    public void updateCourse(int id, String name, int duration, int maxAmountOfStudent, String topic) throws SQLQueryException, CourseAlreadyExistException {
        try (Connection con = connectionPool.getConnection()) {
            transactionManager.doTransaction(con, connection -> {
                try {
                    courseRepository.updateCourse(id, name, duration, maxAmountOfStudent, topic, connection);
                } catch (CourseAlreadyExistException e) {
                    throw new CourseAlreadyExistException(Messages.COURSE_IS_ALREADY_EXISTS, e);
                }
            });
        } catch (SQLException e) {
            throw new CourseAlreadyExistException(Messages.COURSE_IS_ALREADY_EXISTS, e);
        }
    }

    @Override
    public void deleteCourse(int id) throws SQLQueryException {
        try (Connection con = connectionPool.getConnection()) {
            transactionManager.doTransaction(con, connection -> courseRepository.deleteCourse(id, connection));
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
    }

    @Override
    public List<Course> getCoursesByTopicAndTeacher(int teacherId, String topic) throws SQLQueryException {
        try (Connection con = connectionPool.getConnection()) {
            return courseRepository.getCoursesByTopicAndTeacher(teacherId, topic, con);
        } catch (DatabaseException exception) {
            exception.printStackTrace();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Course> getCoursesByTopic(String topic) throws SQLQueryException {
        try (Connection con = connectionPool.getConnection()) {
            return courseRepository.getCoursesByTopic(topic, con);
        } catch (DatabaseException exception) {
            exception.printStackTrace();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Course> getCoursesByTeacher(int teacherId) throws SQLQueryException {
        try (Connection con = connectionPool.getConnection()) {
            return courseRepository.getCoursesByTeacher(teacherId, con);
        } catch (DatabaseException exception) {
            exception.printStackTrace();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Course> getStudentRegisteredCourses(Student student) throws SQLQueryException {
        try (Connection con = connectionPool.getConnection()) {
            return courseRepository.getStudentRegisteredCourses(student, con);
        } catch (DatabaseException exception) {
            exception.printStackTrace();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public void startCourse(int courseId) throws SQLQueryException {
        try (Connection con = connectionPool.getConnection()) {
            courseRepository.startCourse(courseId, con);
        } catch (DatabaseException exception) {
            exception.printStackTrace();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
    }

    @Override
    public void endCourse(int courseId) throws SQLQueryException {
        try (Connection con = connectionPool.getConnection()) {
            courseRepository.endCourse(courseId, con);
        } catch (DatabaseException exception) {
            exception.printStackTrace();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
    }

    @Override
    public void addFinishedCourse(String name, String topic, String teacherFullName, int duration, int mark, int studentId, int courseId) throws SQLQueryException {
        try (Connection con = connectionPool.getConnection()) {
            courseRepository.addFinishedCourse(name, topic, teacherFullName, duration, mark, studentId, courseId, con);
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
    }

    @Override
    public List<FinishedCourse> getStudentFinishedCourses(int studentId) throws SQLQueryException {
        try (Connection con = connectionPool.getConnection()) {
            return courseRepository.getStudentFinishedCourses(studentId, con);
        } catch (DatabaseException exception) {
            exception.printStackTrace();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return Collections.emptyList();
    }
}
