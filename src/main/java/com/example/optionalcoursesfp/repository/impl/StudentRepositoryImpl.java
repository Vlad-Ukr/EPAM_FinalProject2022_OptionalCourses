package com.example.optionalcoursesfp.repository.impl;

import com.example.optionalcoursesfp.entity.FinishedCourse;
import com.example.optionalcoursesfp.entity.Student;
import com.example.optionalcoursesfp.exeption.*;
import com.example.optionalcoursesfp.messages.Messages;
import com.example.optionalcoursesfp.repository.StudentRepository;
import com.example.optionalcoursesfp.repository.UserRepository;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class StudentRepositoryImpl implements StudentRepository {
    private static final String INSERT_STUDENT = "INSERT INTO student(login,full_name) VALUES (?,?)";
    private static final String SET_STUDENT_FIRST_COURSE_NULL = "UPDATE student SET first_course=NULL,first_course_mark=NULL WHERE student.login=?";
    private static final String SET_STUDENT_SECOND_COURSE_NULL = "UPDATE student SET second_course=NULL,second_course_mark=NULL WHERE student.login=?";
    private static final String SET_STUDENT_THIRD_COURSE_NULL = "UPDATE student SET third_course=NULL,third_course_mark=NULL WHERE student.login=?";
    private static final String GET_ALL_STUDENT = "SELECT * from student";
    private static final String BLOCK_STUDENT = "UPDATE student SET student.status='Blocked', first_course_mark=NULL, second_course_mark=NULL, third_course_mark=NULL  WHERE student.login=?";
    private static final String UNBLOCK_STUDENT = "UPDATE student SET student.status='Unblocked' WHERE student.login=?";
    private static final String GET_STUDENT_BY_LOGIN = "SELECT * FROM student WHERE login=?";
    private static final String REGISTER_STUDENT_ON_FIRST_COURSE = "UPDATE student SET student.first_course=? WHERE student.login=?";
    private static final String REGISTER_STUDENT_ON_SECOND_COURSE = "UPDATE student SET student.second_course=? WHERE student.login=?";
    private static final String REGISTER_STUDENT_ON_THIRD_COURSE = "UPDATE student SET student.third_course=? WHERE student.login=?";
    private static final String INCREMENT_STUDENT_AMOUNT_ON_COURSE = "UPDATE courses SET courses.amount_of_student=courses.amount_of_student+1 WHERE courses.id=?";
    private static final String GET_STUDENT_BY_COURSE_ID = "SELECT * FROM student where student.first_course=? or student.second_course=? or student.third_course=?";
    private static final String UPDATE_STUDENT_FIRST_COURSE_MARK = "UPDATE student SET student.first_course_mark=? WHERE student.id=?";
    private static final String UPDATE_STUDENT_SECOND_COURSE_MARK = "UPDATE student SET student.second_course_mark=? WHERE student.id=?";
    private static final String UPDATE_STUDENT_THIRD_COURSE_MARK = "UPDATE student SET student.third_course_mark=? WHERE student.id=?";
    private static final String GET_STUDENT_FROM_FINISHED_COURSES = "SELECT * FROM optionaldb.finishedcourses LEFT OUTER JOIN student ON studentId=student.id WHERE finishedcourses.courseId=?";
    private static final Logger log = Logger.getLogger(StudentRepositoryImpl.class);

    @Override
    public void insertStudent(String login, String password, String fullName, Connection connection, UserRepository userRepository) throws SQLQueryException {
        try {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_STUDENT)) {
                ps.setString(1, login);
                ps.setString(2, fullName);
                ps.executeUpdate();
                log.info("student-" + login + " was added into database");
            } catch (SQLException e) {
                throw new SQLQueryException(e.getMessage(), e);
            }
        } catch (SQLQueryException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
    }


    @Override
    public List<Student> getAllStudent(Connection connection) throws SQLQueryException {
        List<Student> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_ALL_STUDENT)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result.add(new Student(resultSet.getString("login"), resultSet.getInt("id")
                        , resultSet.getString("full_name"), resultSet.getString("status")
                        , resultSet.getInt("first_course"), resultSet.getInt("second_course"), resultSet.getInt("third_course")
                        , resultSet.getInt("first_course_mark"), resultSet.getInt("second_course_mark"), resultSet.getInt("third_course_mark")));
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public void blockStudent(String login, Connection connection) throws SQLQueryException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(BLOCK_STUDENT)) {
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();
            log.info("student-" + login + " was blocked");
        } catch (SQLException e) {
            log.info("student-" + login + " was blocked ERROR");
            throw new SQLQueryException(e.getMessage(), e);
        }
    }

    @Override
    public void unBlockStudent(String login, Connection connection) throws SQLQueryException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UNBLOCK_STUDENT)) {
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();
            log.info("student-" + login + " was unblocked");
        } catch (SQLException e) {
            log.info("student-" + login + " was unblocked ERROR");
            throw new SQLQueryException(e.getMessage(), e);
        }
    }

    @Override
    public Student getStudentByLogin(String login, Connection connection) throws SQLQueryException {
        Student student = new Student();
        try (PreparedStatement ps = connection.prepareStatement(GET_STUDENT_BY_LOGIN)) {
            ps.setString(1, login);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                student.setId(resultSet.getInt("id"));
                student.setLogin(resultSet.getString("login"));
                student.setFullName(resultSet.getString("full_name"));
                student.setStatus(resultSet.getString("status"));
                student.setFirstCourseId(resultSet.getInt("first_course"));
                student.setSecondCourseId(resultSet.getInt("second_course"));
                student.setThirdCourseId(resultSet.getInt("third_course"));
                student.setFirstCourseMark(resultSet.getInt("first_course_mark"));
                student.setSecondCourseMark(resultSet.getInt("second_course_mark"));
                student.setThirdCourseMark(resultSet.getInt("third_course_mark"));
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return student;
    }

    @Override
    public void registerStudentOnCourse(Student student, int courseId, Connection connection) throws SQLQueryException, StudentAlreadyRegisteredException, MaxAmountOfRegistrationException {
        log.info(student);
        String studentQuery;
        if (student.isTheStudentAlreadyRegistered(courseId)) {
            log.info("student has already registered");
            throw new StudentAlreadyRegisteredException(Messages.STUDENT_ALREADY_REGISTERED);
        } else if (student.getFirstCourseId() == 0) {
            student.setFirstCourseId(courseId);
            studentQuery = REGISTER_STUDENT_ON_FIRST_COURSE;
        } else if (student.getSecondCourseId() == 0) {
            student.setSecondCourseId(courseId);
            studentQuery = REGISTER_STUDENT_ON_SECOND_COURSE;
        } else if (student.getThirdCourseId() == 0) {
            student.setThirdCourseId(courseId);
            studentQuery = REGISTER_STUDENT_ON_THIRD_COURSE;
        } else {
            throw new MaxAmountOfRegistrationException(Messages.MAX_AMOUNT_OF_REGISTRATIONS);
        }
        try (PreparedStatement preparedStatementStudentUpdate = connection.prepareStatement(studentQuery)
             ; PreparedStatement preparedStatementCourseUpdate = connection.prepareStatement(INCREMENT_STUDENT_AMOUNT_ON_COURSE)) {
            preparedStatementStudentUpdate.setInt(1, courseId);
            preparedStatementStudentUpdate.setString(2, student.getLogin());
            preparedStatementStudentUpdate.executeUpdate();
            preparedStatementCourseUpdate.setInt(1, courseId);
            preparedStatementCourseUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
    }

    @Override
    public List<Student> getStudentsByCourseId(int courseId, Connection connection) throws SQLQueryException {
        List<Student> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_STUDENT_BY_COURSE_ID)) {
            ps.setInt(1, courseId);
            ps.setInt(2, courseId);
            ps.setInt(3, courseId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result.add(new Student(resultSet.getString("login"), resultSet.getInt("id")
                        , resultSet.getString("full_name"), resultSet.getString("status")
                        , resultSet.getInt("first_course"), resultSet.getInt("second_course"), resultSet.getInt("third_course")
                        , resultSet.getInt("first_course_mark"), resultSet.getInt("second_course_mark"), resultSet.getInt("third_course_mark")));
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public void updateStudentMark(Student student, int courseNumber, int mark, Connection connection) throws SQLQueryException {
        String query;
        if (courseNumber == 1) {
            query = UPDATE_STUDENT_FIRST_COURSE_MARK;
        } else if (courseNumber == 2) {
            query = UPDATE_STUDENT_SECOND_COURSE_MARK;
        } else {
            query = UPDATE_STUDENT_THIRD_COURSE_MARK;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, mark);
            preparedStatement.setInt(2, student.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
    }

    @Override
    public void setStudentCourseNull(Student student, int courseNumber, Connection connection) throws SQLQueryException {
        String query;
        if (courseNumber == 1) {
            log.info(student.getLogin() + " delete first course id and mark");
            query = SET_STUDENT_FIRST_COURSE_NULL;
        } else if (courseNumber == 2) {
            log.info(student.getLogin() + " delete first course id and mark");
            query = SET_STUDENT_SECOND_COURSE_NULL;
        } else {
            log.info(student.getLogin() + " delete first course id and mark");
            query = SET_STUDENT_THIRD_COURSE_NULL;
        }
        log.info("setStudentCourseNull" + student + " " + query);
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, student.getLogin());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
    }

    @Override
    public List<FinishedCourse> getStudentsFromFinishedCourses(int courseId, Connection connection) throws SQLQueryException {
        List<FinishedCourse> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_STUDENT_FROM_FINISHED_COURSES)) {
            ps.setInt(1, courseId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result.add(new FinishedCourse(resultSet.getInt("id"), resultSet.getString("name")
                        , resultSet.getInt("duration"), resultSet.getString("topic")
                        , resultSet.getString("status"), resultSet.getString("teacherName"), resultSet.getString("full_name"), resultSet.getInt("mark")));
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return result;
    }
}

