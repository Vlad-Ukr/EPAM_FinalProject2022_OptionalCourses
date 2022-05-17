package com.example.optionalcoursesfp.repository.impl;

import com.example.optionalcoursesfp.entity.Course;
import com.example.optionalcoursesfp.entity.FinishedCourse;
import com.example.optionalcoursesfp.entity.Student;
import com.example.optionalcoursesfp.exeption.CourseAlreadyExistException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.messages.Messages;
import com.example.optionalcoursesfp.repository.CourseRepository;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseRepositoryImpl implements CourseRepository {
    private static final String GET_ALL_COURSES = "SELECT * FROM courses LEFT OUTER JOIN teacher ON teacher_id=teacher.id";
    private static final String GET_COURSE_BY_ID = "SELECT * FROM courses WHERE courses.id=?";
    private static final String ADD_COURSE="INSERT INTO courses(name,duration,max_amount_of_student,topic,teacher_id) VALUES (?,?,?,?,?)";
    private static final String UPDATE_COURSE="UPDATE courses SET courses.name=?, courses.duration=?, courses.max_amount_of_student=?, courses.topic=? WHERE courses.id=?";
    private static final String DELETE_COURSE="DELETE FROM courses WHERE courses.id=?";
    private static final String GET_COURSES_BY_TOPIC_AND_TEACHER="SELECT * FROM courses LEFT OUTER JOIN teacher ON teacher_id=teacher.id where topic=? AND teacher_id=?";
    private static final String GET_COURSES_BY_TOPIC="SELECT * FROM courses LEFT OUTER JOIN teacher ON teacher_id=teacher.id WHERE topic=?";
    private static final String GET_COURSES_BY_TEACHER="SELECT * FROM courses LEFT OUTER JOIN teacher ON teacher_id=teacher.id WHERE teacher_id=?";
    private static final String GET_STUDENT_REGISTERED_COURSES="SELECT * FROM courses LEFT OUTER JOIN teacher ON teacher_id=teacher.id WHERE courses.id=? OR courses.id=? OR courses.id=?";
    private static final String START_COURSE="UPDATE courses SET courses.status='In process' WHERE courses.id=?";
    private static final String END_COURSE="UPDATE courses SET courses.status='Ended' WHERE courses.id=?";
    private static final String ADD_FINISHED_COURSE="INSERT INTO finishedcourses(name,topic,teacherName,duration,status,mark,studentId,courseId) VALUES (?,?,?,?,?,?,?,?)";
    private static final String GET_STUDENT_FINISHED_COURSES="SELECT  * FROM finishedcourses WHERE studentId=?";
    private static final Logger log = Logger.getLogger(CourseRepositoryImpl.class);

    @Override
    public List<Course> getAllCourses(Connection connection) throws SQLQueryException {
        log.info("Getting all courses");
        List<Course> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_ALL_COURSES)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result.add(new Course(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("duration")
                        , resultSet.getInt("amount_of_student"), resultSet.getInt("max_amount_of_student"), resultSet.getString("topic")
                        , resultSet.getInt("teacher_id"), resultSet.getString("status"), resultSet.getString("full_name")));
            }
        } catch (SQLException e) {
            throw new SQLQueryException(Messages.ERR_CANNOT_EXECUTE_QUERY, e);
        }
        return result;
    }

    @Override
    public Course getCourseById(int courseId, Connection connection) throws SQLQueryException {
        try (PreparedStatement ps = connection.prepareStatement(GET_COURSE_BY_ID)) {
            ps.setInt(1, courseId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                return new Course(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("duration")
                        , resultSet.getInt("amount_of_student"), resultSet.getInt("max_amount_of_student"), resultSet.getString("topic")
                        , resultSet.getInt("teacher_id"), resultSet.getString("status"));
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void addCourse(String name, int duration, int maxAmountOfStudent, String topic, int teacherId, Connection connection) throws SQLQueryException, CourseAlreadyExistException {
        log.info("adding course " + name);
        try (PreparedStatement ps = connection.prepareStatement(ADD_COURSE)) {
            ps.setString(1, name);
            ps.setInt(2, duration);
            ps.setInt(3, maxAmountOfStudent);
            ps.setString(4, topic);
            ps.setInt(5, teacherId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new CourseAlreadyExistException(Messages.COURSE_IS_ALREADY_EXISTS, e);
        }
    }

    @Override
    public void updateCourse(int id, String name, int duration, int maxAmountOfStudent, String topic, Connection connection) throws SQLQueryException, CourseAlreadyExistException {
        log.info("updating course " + name);
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COURSE)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, duration);
            preparedStatement.setInt(3, maxAmountOfStudent);
            preparedStatement.setString(4, topic);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
            log.info("course was update");
        } catch (SQLException e) {
            throw new CourseAlreadyExistException(Messages.COURSE_IS_ALREADY_EXISTS, e);
        }
    }

    @Override
    public void deleteCourse(int id, Connection connection) throws SQLQueryException {
        log.info("deleting course " + id);
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COURSE)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            log.info("course was update");
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
    }

    @Override
    public List<Course> getCoursesByTopicAndTeacher(int teacherId, String topic, Connection connection) throws SQLQueryException {
        List<Course> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_COURSES_BY_TOPIC_AND_TEACHER)) {
            ps.setString(1, topic);
            ps.setInt(2, teacherId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result.add(new Course(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("duration")
                        , resultSet.getInt("amount_of_student"), resultSet.getInt("max_amount_of_student"), resultSet.getString("topic")
                        , resultSet.getInt("teacher_id"), resultSet.getString("status"), resultSet.getString("full_name")));
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<Course> getCoursesByTopic(String topic, Connection connection) throws SQLQueryException {
        List<Course> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_COURSES_BY_TOPIC)) {
            ps.setString(1, topic);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result.add(new Course(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("duration")
                        , resultSet.getInt("amount_of_student"), resultSet.getInt("max_amount_of_student"), resultSet.getString("topic")
                        , resultSet.getInt("teacher_id"), resultSet.getString("status"), resultSet.getString("full_name")));
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<Course> getCoursesByTeacher(int teacherId, Connection connection) throws SQLQueryException {
        List<Course> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_COURSES_BY_TEACHER)) {
            ps.setInt(1, teacherId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result.add(new Course(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("duration")
                        , resultSet.getInt("amount_of_student"), resultSet.getInt("max_amount_of_student"), resultSet.getString("topic")
                        , resultSet.getInt("teacher_id"), resultSet.getString("status"), resultSet.getString("full_name")));
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public List<Course> getStudentRegisteredCourses(Student student, Connection connection) throws SQLQueryException {
        List<Course> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_STUDENT_REGISTERED_COURSES)) {
            ps.setInt(1, student.getFirstCourseId());
            ps.setInt(2, student.getSecondCourseId());
            ps.setInt(3, student.getThirdCourseId());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result.add(new Course(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("duration")
                        , resultSet.getInt("amount_of_student"), resultSet.getInt("max_amount_of_student"), resultSet.getString("topic")
                        , resultSet.getInt("teacher_id"), resultSet.getString("status"), resultSet.getString("full_name")));
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public void startCourse(int courseId, Connection connection) throws SQLQueryException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(START_COURSE)) {
            preparedStatement.setInt(1, courseId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
    }

    @Override
    public void endCourse(int courseId, Connection connection) throws SQLQueryException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(END_COURSE)) {
            preparedStatement.setInt(1, courseId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
    }

    @Override
    public void addFinishedCourse(String name, String topic, String teacherFullName, int duration, int mark, int studentId, int courseId, Connection connection) throws SQLQueryException {
        try (PreparedStatement ps = connection.prepareStatement(ADD_FINISHED_COURSE)) {
            ps.setString(1, name);
            ps.setString(2, topic);
            ps.setString(3, teacherFullName);
            ps.setInt(4, duration);
            ps.setString(5, "Ended");
            ps.setInt(6, mark);
            ps.setInt(7, studentId);
            ps.setInt(8, courseId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
    }

    @Override
    public List<FinishedCourse> getStudentFinishedCourses(int studentId, Connection connection) throws SQLQueryException {
        List<FinishedCourse> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_STUDENT_FINISHED_COURSES)) {
            ps.setInt(1, studentId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                result.add(new FinishedCourse(resultSet.getInt("id"), resultSet.getString("name")
                        , resultSet.getInt("duration"), resultSet.getString("topic")
                        , resultSet.getString("status"), resultSet.getString("teacherName"), resultSet.getInt("mark")));
            }
        } catch (SQLException e) {
            throw new SQLQueryException(e.getMessage(), e);
        }
        return result;
    }
}