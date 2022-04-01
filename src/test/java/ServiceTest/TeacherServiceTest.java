package ServiceTest;

import com.example.optionalcoursesfp.entity.Teacher;
import com.example.optionalcoursesfp.exeption.DatabaseException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.repository.TeacherRepository;
import com.example.optionalcoursesfp.repository.UserRepository;
import com.example.optionalcoursesfp.repository.impl.TeacherRepositoryImpl;
import com.example.optionalcoursesfp.repository.impl.UserRepositoryImpl;
import com.example.optionalcoursesfp.service.TeacherService;
import com.example.optionalcoursesfp.service.impl.TeacherServiceImpl;
import com.example.optionalcoursesfp.util.connection.ConnectionPool;
import com.example.optionalcoursesfp.util.transaction.TransactionManager;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TeacherServiceTest {
    @Test
    public void getAllTeachersTest() {
        UserRepository userRepository = new UserRepositoryImpl();
        TeacherRepository teacherRepository = new TeacherRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        TeacherService teacherService = new TeacherServiceImpl(userRepository, teacherRepository, connectionPool, transactionManager);
        try {
            when(connectionPool.getConnection()).thenReturn(connection);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        try {
            when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
            when(resultSet.getInt("id")).thenReturn(1).thenReturn(2);
            when(resultSet.getString("login")).thenReturn("login1").thenReturn("login2");
            when(resultSet.getString("full_name")).thenReturn("full_name1").thenReturn("full_name2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Teacher> expected=new ArrayList<>();
        expected.add(new Teacher(1,"login1","full_name1"));
        expected.add(new Teacher(2,"login2","full_name2"));
        List<Teacher> actual=new ArrayList<>();
        try {
          actual=teacherService.getAllTeachers();
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
        assertEquals(expected,actual);
    }

    @Test
    public void addTeacher() {
        UserRepository userRepository = new UserRepositoryImpl();
        TeacherRepository teacherRepository = new TeacherRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        TeacherService teacherService = new TeacherServiceImpl(userRepository, teacherRepository, connectionPool, transactionManager);
        try {
            when(connectionPool.getConnection()).thenReturn(connection);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        try {
            when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            teacherService.addTeacher("login", "fullName", "password");
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTeacherByLoginTest() {
        UserRepository userRepository = new UserRepositoryImpl();
        TeacherRepository teacherRepository = new TeacherRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        TeacherService teacherService = new TeacherServiceImpl(userRepository, teacherRepository, connectionPool, transactionManager);
        try {
            when(connectionPool.getConnection()).thenReturn(connection);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        try {
            when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getString("login"))
                    .thenReturn("kovalVit@gmail.com");
            when(resultSet.getInt("id")).thenReturn(1);
            when(resultSet.getString("full_name"))
                    .thenReturn("Коваль Виталий Андреевич");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Teacher expected = new Teacher(1, "kovalVit@gmail.com", "Коваль Виталий Андреевич");
        Teacher actual = null;
        try {
            actual = teacherService.getTeacherByLogin("kovalVit@gmail.com");
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
        assertEquals(expected, actual);
    }
}
