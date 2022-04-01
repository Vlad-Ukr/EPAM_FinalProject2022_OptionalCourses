package ServiceTest;

import com.example.optionalcoursesfp.entity.FinishedCourse;
import com.example.optionalcoursesfp.entity.Student;
import com.example.optionalcoursesfp.entity.Teacher;
import com.example.optionalcoursesfp.exeption.DatabaseException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.repository.StudentRepository;
import com.example.optionalcoursesfp.repository.TeacherRepository;
import com.example.optionalcoursesfp.repository.UserRepository;
import com.example.optionalcoursesfp.repository.impl.StudentRepositoryImpl;
import com.example.optionalcoursesfp.repository.impl.TeacherRepositoryImpl;
import com.example.optionalcoursesfp.repository.impl.UserRepositoryImpl;
import com.example.optionalcoursesfp.service.StudentService;
import com.example.optionalcoursesfp.service.TeacherService;
import com.example.optionalcoursesfp.service.impl.StudentServiceImpl;
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

public class StudentServiceTest {
    @Test
    public void insertStudentTest(){
        UserRepository userRepository = new UserRepositoryImpl();
        StudentRepository studentRepository=new StudentRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        StudentService studentService=new StudentServiceImpl(userRepository,studentRepository,connectionPool,transactionManager);
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
           studentService.insertStudent("login","password","full_name");
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void setStudentMarkAndCourseIdNullTest(){
        UserRepository userRepository = new UserRepositoryImpl();
        StudentRepository studentRepository=new StudentRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        StudentService studentService=new StudentServiceImpl(userRepository,studentRepository,connectionPool,transactionManager);
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
        Student testStudent=new Student();
        testStudent.setLogin("login");
        try {
            studentRepository.setStudentCourseNull(testStudent,1,connection);
            studentRepository.setStudentCourseNull(testStudent,2,connection);
            studentRepository.setStudentCourseNull(testStudent,3,connection);
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void grateStudentTest(){
        UserRepository userRepository = new UserRepositoryImpl();
        StudentRepository studentRepository=new StudentRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        StudentService studentService=new StudentServiceImpl(userRepository,studentRepository,connectionPool,transactionManager);
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
        Student testStudent=new Student();
        testStudent.setLogin("login");
        try {
            studentService.grateStudent(testStudent,1,50);
            studentService.grateStudent(testStudent,2,50);
            studentService.grateStudent(testStudent,3,50);
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getAllStudentTest(){
        UserRepository userRepository = new UserRepositoryImpl();
        StudentRepository studentRepository=new StudentRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        StudentService studentService=new StudentServiceImpl(userRepository,studentRepository,connectionPool,transactionManager);
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
            when(resultSet.getString("login")).thenReturn("login1").thenReturn("login2");
            when(resultSet.getInt("id")).thenReturn(1).thenReturn(2);
            when(resultSet.getString("full_name")).thenReturn("full_name1").thenReturn("full_name2");
            when(resultSet.getString("status")).thenReturn("Разблокирован").thenReturn("Разблокирован");
            when(resultSet.getInt("first_course")).thenReturn(1).thenReturn(1);
            when(resultSet.getInt("second_course")).thenReturn(2).thenReturn(2);
            when(resultSet.getInt("third_course")).thenReturn(3).thenReturn(3);
            when(resultSet.getInt("first_course_mark")).thenReturn(10).thenReturn(10);
            when(resultSet.getInt("second_course_mark")).thenReturn(20).thenReturn(20);
            when(resultSet.getInt("third_course_mark")).thenReturn(30).thenReturn(30);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Student> expected=new ArrayList<>();
        expected.add(new Student("login1",1,"full_name1","Разблокирован",1,2,3,10,20,30));
        expected.add(new Student("login2",2,"full_name2","Разблокирован",1,2,3,10,20,30));
        List<Student> actual=new ArrayList<>();
        try {
           actual=studentService.getAllStudent();
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
        assertEquals(expected,actual);
    }
    @Test
    public void unBlockStudentTest(){
        UserRepository userRepository = new UserRepositoryImpl();
        StudentRepository studentRepository=new StudentRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        StudentService studentService=new StudentServiceImpl(userRepository,studentRepository,connectionPool,transactionManager);
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
        Student testStudent=new Student();
        testStudent.setLogin("login");
        try {
          studentService.unBlockStudent(testStudent.getLogin());
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void BlockStudentTest(){
        UserRepository userRepository = new UserRepositoryImpl();
        StudentRepository studentRepository=new StudentRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        StudentService studentService=new StudentServiceImpl(userRepository,studentRepository,connectionPool,transactionManager);
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
        Student testStudent=new Student();
        testStudent.setLogin("login");
        try {
            studentService.blockStudent(testStudent.getLogin());
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getStudentByLoginTest(){
        UserRepository userRepository = new UserRepositoryImpl();
        StudentRepository studentRepository=new StudentRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        StudentService studentService=new StudentServiceImpl(userRepository,studentRepository,connectionPool,transactionManager);
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
            when(resultSet.getString("login")).thenReturn("login");
            when(resultSet.getInt("id")).thenReturn(1);
            when(resultSet.getString("full_name")).thenReturn("full_name");
            when(resultSet.getString("status")).thenReturn("Разблокирован");
            when(resultSet.getInt("first_course")).thenReturn(1);
            when(resultSet.getInt("second_course")).thenReturn(2);
            when(resultSet.getInt("third_course")).thenReturn(3);
            when(resultSet.getInt("first_course_mark")).thenReturn(10);
            when(resultSet.getInt("second_course_mark")).thenReturn(20);
            when(resultSet.getInt("third_course_mark")).thenReturn(30);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Student expected=new Student("login",1,"full_name","Разблокирован",1,2,3,10,20,30);
        Student actual = null;
        try {
            actual=studentService.getStudentByLogin("login");
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
        assertEquals(expected,actual);
    }
    @Test
    public void registerStudentOnCourseTest(){
        UserRepository userRepository = new UserRepositoryImpl();
        StudentRepository studentRepository=new StudentRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        StudentService studentService=new StudentServiceImpl(userRepository,studentRepository,connectionPool,transactionManager);
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
        Student testStudent=new Student();
        testStudent.setLogin("login");
        testStudent.setStatus("Разблокирован");
        try {
           studentRepository.registerStudentOnCourse(testStudent,1,connection);
            studentRepository.registerStudentOnCourse(testStudent,2,connection);
            studentRepository.registerStudentOnCourse(testStudent,3,connection);
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getStudentByCourseIdTest(){
        UserRepository userRepository = new UserRepositoryImpl();
        StudentRepository studentRepository=new StudentRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        StudentService studentService=new StudentServiceImpl(userRepository,studentRepository,connectionPool,transactionManager);
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
            when(resultSet.getString("login")).thenReturn("login1").thenReturn("login2");
            when(resultSet.getInt("id")).thenReturn(1).thenReturn(2);
            when(resultSet.getString("full_name")).thenReturn("full_name1").thenReturn("full_name2");
            when(resultSet.getString("status")).thenReturn("Разблокирован").thenReturn("Разблокирован");
            when(resultSet.getInt("first_course")).thenReturn(1).thenReturn(1);
            when(resultSet.getInt("second_course")).thenReturn(2).thenReturn(2);
            when(resultSet.getInt("third_course")).thenReturn(3).thenReturn(3);
            when(resultSet.getInt("first_course_mark")).thenReturn(10).thenReturn(10);
            when(resultSet.getInt("second_course_mark")).thenReturn(20).thenReturn(20);
            when(resultSet.getInt("third_course_mark")).thenReturn(30).thenReturn(30);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Student> actual=new ArrayList<>();
        List<Student> expected=new ArrayList<>();
        expected.add(new Student("login1",1,"full_name1","Разблокирован",1,2,3,10,20,30));
        expected.add(new Student("login2",2,"full_name2","Разблокирован",1,2,3,10,20,30));
        try {
            actual=studentService.getStudentsByCourseId(1);
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
        assertEquals(expected,actual);
    }
    @Test
    public void getStudentsFromFinishedCoursesTest(){
        UserRepository userRepository = new UserRepositoryImpl();
        StudentRepository studentRepository=new StudentRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        StudentService studentService=new StudentServiceImpl(userRepository,studentRepository,connectionPool,transactionManager);
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
            when(resultSet.getString("name")).thenReturn("course1").thenReturn("course2");
            when(resultSet.getInt("duration")).thenReturn(10).thenReturn(10);
            when(resultSet.getString("topic")).thenReturn("topic1").thenReturn("topic2");
            when(resultSet.getString("status")).thenReturn("status1").thenReturn("status2");
            when(resultSet.getString("teacherName")).thenReturn("teacherName1").thenReturn("teacherName2");
            when(resultSet.getString("full_name")).thenReturn("full_name1").thenReturn("full_name2");
            when(resultSet.getInt("mark")).thenReturn(10).thenReturn(10);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<FinishedCourse> actual=new ArrayList<>();
        List<FinishedCourse> expected=new ArrayList<>();
        expected.add(new FinishedCourse(1,"course1",10,"topic1","status1","teacherName1","full_name1",10));
        expected.add(new FinishedCourse(2,"course2",10,"topic2","status2","teacherName2","full_name2",10));
        try {
            actual=studentService.getStudentsFromFinishedCourses(1);
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
        assertEquals(expected.toString(),actual.toString());
    }
}
