package ServiceTest;

import com.example.optionalcoursesfp.entity.Course;
import com.example.optionalcoursesfp.entity.FinishedCourse;
import com.example.optionalcoursesfp.entity.Student;
import com.example.optionalcoursesfp.exeption.CourseAlreadyExistException;
import com.example.optionalcoursesfp.exeption.DatabaseException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.repository.CourseRepository;
import com.example.optionalcoursesfp.repository.StudentRepository;
import com.example.optionalcoursesfp.repository.UserRepository;
import com.example.optionalcoursesfp.repository.impl.CourseRepositoryImpl;
import com.example.optionalcoursesfp.repository.impl.StudentRepositoryImpl;
import com.example.optionalcoursesfp.repository.impl.UserRepositoryImpl;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.StudentService;
import com.example.optionalcoursesfp.service.impl.CourseServiceImp;
import com.example.optionalcoursesfp.service.impl.StudentServiceImpl;
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

public class CourseServiceTest {
    @Test
    public void getAllCoursesTest(){
        CourseRepository courseRepository=new CourseRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
      CourseService courseService =new CourseServiceImp(courseRepository,connectionPool,transactionManager);
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
            when(resultSet.getString("name")).thenReturn("name1").thenReturn("name2");
            when(resultSet.getInt("duration")).thenReturn(10).thenReturn(10);
            when(resultSet.getInt("amount_of_student")).thenReturn(10).thenReturn(10);
            when(resultSet.getInt("max_amount_of_student")).thenReturn(10).thenReturn(10);
            when(resultSet.getString("topic")).thenReturn("topic1").thenReturn("topic2");
            when(resultSet.getInt("teacher_id")).thenReturn(1).thenReturn(2);
            when(resultSet.getString("status")).thenReturn("status1").thenReturn("status2");
            when(resultSet.getString("full_name")).thenReturn("full_name1").thenReturn("full_name2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Course> expected=new ArrayList<>();
       expected.add(new Course(1,"name1",10,10,10,"topic1",1,"status1","full_name1"));
        expected.add(new Course(2,"name2",10,10,10,"topic2",2,"status2","full_name2"));
        List<Course> actual=new ArrayList<>();
        try {
            actual=courseService.getAllCourses();
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
        assertEquals(expected.toString(),actual.toString());
    }
    @Test
    public void getCourseByIdTest(){
        CourseRepository courseRepository=new CourseRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        CourseService courseService =new CourseServiceImp(courseRepository,connectionPool,transactionManager);
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
            when(resultSet.getInt("id")).thenReturn(1);
            when(resultSet.getString("name")).thenReturn("name");
            when(resultSet.getInt("duration")).thenReturn(10);
            when(resultSet.getInt("amount_of_student")).thenReturn(10);
            when(resultSet.getInt("max_amount_of_student")).thenReturn(10);
            when(resultSet.getString("topic")).thenReturn("topic");
            when(resultSet.getInt("teacher_id")).thenReturn(1);
            when(resultSet.getString("status")).thenReturn("status");
        } catch (SQLException e) {
            e.printStackTrace();
        }
      Course expected=new Course(1,"name",10,10,10,"topic",1,"status");
        Course actual = new Course();
        try {
            actual=courseService.getCourseById(1);
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
        assertEquals(expected.toString(),actual.toString());
    }
    @Test
    public void addCourseTest(){
        CourseRepository courseRepository=new CourseRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        CourseService courseService =new CourseServiceImp(courseRepository,connectionPool,transactionManager);
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
            courseService.addCourse("name",10,10,"topic",1);
        } catch (SQLQueryException e) {
            e.printStackTrace();
        } catch (CourseAlreadyExistException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void updateCourseTest() throws SQLQueryException, CourseAlreadyExistException {
        CourseRepository courseRepository=new CourseRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        CourseService courseService =new CourseServiceImp(courseRepository,connectionPool,transactionManager);
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
        }courseRepository.updateCourse(1,"name",10,10,"topic",connection);
    }
    @Test
    public void deleteCourseTest(){
        CourseRepository courseRepository=new CourseRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        CourseService courseService =new CourseServiceImp(courseRepository,connectionPool,transactionManager);
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
            courseRepository.deleteCourse(1,connection);
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getCoursesByTopicAndTeacher(){
        CourseRepository courseRepository=new CourseRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        CourseService courseService =new CourseServiceImp(courseRepository,connectionPool,transactionManager);
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
            when(resultSet.getString("name")).thenReturn("name1").thenReturn("name2");
            when(resultSet.getInt("duration")).thenReturn(10).thenReturn(10);
            when(resultSet.getInt("amount_of_student")).thenReturn(10).thenReturn(10);
            when(resultSet.getInt("max_amount_of_student")).thenReturn(10).thenReturn(10);
            when(resultSet.getString("topic")).thenReturn("topic1").thenReturn("topic2");
            when(resultSet.getInt("teacher_id")).thenReturn(1).thenReturn(2);
            when(resultSet.getString("status")).thenReturn("status1").thenReturn("status2");
            when(resultSet.getString("full_name")).thenReturn("full_name1").thenReturn("full_name2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Course> expected=new ArrayList<>();
        expected.add(new Course(1,"name1",10,10,10,"topic1",1,"status1","full_name1"));
        expected.add(new Course(2,"name2",10,10,10,"topic2",2,"status2","full_name2"));
        List<Course> actual=new ArrayList<>();
        try {
            actual=courseService.getCoursesByTopicAndTeacher(1,"topic");
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
        assertEquals(expected.toString(),actual.toString());
    }
    @Test
    public void getCoursesByTopic(){
        CourseRepository courseRepository=new CourseRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        CourseService courseService =new CourseServiceImp(courseRepository,connectionPool,transactionManager);
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
            when(resultSet.getString("name")).thenReturn("name1").thenReturn("name2");
            when(resultSet.getInt("duration")).thenReturn(10).thenReturn(10);
            when(resultSet.getInt("amount_of_student")).thenReturn(10).thenReturn(10);
            when(resultSet.getInt("max_amount_of_student")).thenReturn(10).thenReturn(10);
            when(resultSet.getString("topic")).thenReturn("topic1").thenReturn("topic2");
            when(resultSet.getInt("teacher_id")).thenReturn(1).thenReturn(2);
            when(resultSet.getString("status")).thenReturn("status1").thenReturn("status2");
            when(resultSet.getString("full_name")).thenReturn("full_name1").thenReturn("full_name2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Course> expected=new ArrayList<>();
        expected.add(new Course(1,"name1",10,10,10,"topic1",1,"status1","full_name1"));
        expected.add(new Course(2,"name2",10,10,10,"topic2",2,"status2","full_name2"));
        List<Course> actual=new ArrayList<>();
        try {
            actual=courseService.getCoursesByTopic("topic");
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
        assertEquals(expected.toString(),actual.toString());
    }
    @Test
    public void getCoursesByTeacher(){
        CourseRepository courseRepository=new CourseRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        CourseService courseService =new CourseServiceImp(courseRepository,connectionPool,transactionManager);
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
            when(resultSet.getString("name")).thenReturn("name1").thenReturn("name2");
            when(resultSet.getInt("duration")).thenReturn(10).thenReturn(10);
            when(resultSet.getInt("amount_of_student")).thenReturn(10).thenReturn(10);
            when(resultSet.getInt("max_amount_of_student")).thenReturn(10).thenReturn(10);
            when(resultSet.getString("topic")).thenReturn("topic1").thenReturn("topic2");
            when(resultSet.getInt("teacher_id")).thenReturn(1).thenReturn(2);
            when(resultSet.getString("status")).thenReturn("status1").thenReturn("status2");
            when(resultSet.getString("full_name")).thenReturn("full_name1").thenReturn("full_name2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Course> expected=new ArrayList<>();
        expected.add(new Course(1,"name1",10,10,10,"topic1",1,"status1","full_name1"));
        expected.add(new Course(2,"name2",10,10,10,"topic2",2,"status2","full_name2"));
        List<Course> actual=new ArrayList<>();
        try {
            actual=courseService.getCoursesByTeacher(1);
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
        assertEquals(expected.toString(),actual.toString());
    }
    @Test
    public void getStudentRegisteredCourses(){
        CourseRepository courseRepository=new CourseRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        CourseService courseService =new CourseServiceImp(courseRepository,connectionPool,transactionManager);
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
            when(resultSet.getString("name")).thenReturn("name1").thenReturn("name2");
            when(resultSet.getInt("duration")).thenReturn(10).thenReturn(10);
            when(resultSet.getInt("amount_of_student")).thenReturn(10).thenReturn(10);
            when(resultSet.getInt("max_amount_of_student")).thenReturn(10).thenReturn(10);
            when(resultSet.getString("topic")).thenReturn("topic1").thenReturn("topic2");
            when(resultSet.getInt("teacher_id")).thenReturn(1).thenReturn(2);
            when(resultSet.getString("status")).thenReturn("status1").thenReturn("status2");
            when(resultSet.getString("full_name")).thenReturn("full_name1").thenReturn("full_name2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Course> expected=new ArrayList<>();
        expected.add(new Course(1,"name1",10,10,10,"topic1",1,"status1","full_name1"));
        expected.add(new Course(2,"name2",10,10,10,"topic2",2,"status2","full_name2"));
        List<Course> actual=new ArrayList<>();
        try {
            actual=courseService.getStudentRegisteredCourses(new Student("login",1,"full_name","Разблокирован",1,2,3,10,20,30));
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
        assertEquals(expected.toString(),actual.toString());
    }
    @Test
    public void startCourseTest(){
        CourseRepository courseRepository=new CourseRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        CourseService courseService =new CourseServiceImp(courseRepository,connectionPool,transactionManager);
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
            courseService.startCourse(1);
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void endCourseTest(){
        CourseRepository courseRepository=new CourseRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        CourseService courseService =new CourseServiceImp(courseRepository,connectionPool,transactionManager);
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
            courseService.endCourse(1);
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void addFinishedCourse(){
        {
            CourseRepository courseRepository=new CourseRepositoryImpl();
            TransactionManager transactionManager = mock(TransactionManager.class);
            Connection connection = mock(Connection.class);
            ConnectionPool connectionPool = mock(ConnectionPool.class);
            CourseService courseService =new CourseServiceImp(courseRepository,connectionPool,transactionManager);
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
                courseService.addFinishedCourse("name","topic","full_name",10,10,10,10);
            } catch (SQLQueryException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void getStudentFinishedCourse(){
        CourseRepository courseRepository=new CourseRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
        CourseService courseService =new CourseServiceImp(courseRepository,connectionPool,transactionManager);
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
        expected.add(new FinishedCourse(1,"course1",10,"topic1","status1","teacherName1",null,10));
        expected.add(new FinishedCourse(2,"course2",10,"topic2","status2","teacherName2",null,10));
        try {
            actual=courseService.getStudentFinishedCourses(1);
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
        assertEquals(expected.toString(),actual.toString());
    }
}
