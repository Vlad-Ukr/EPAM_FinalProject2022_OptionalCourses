package ServiceTest;

import com.example.optionalcoursesfp.entity.User;
import com.example.optionalcoursesfp.entity.UserRole;
import com.example.optionalcoursesfp.exeption.DatabaseException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.repository.UserRepository;
import com.example.optionalcoursesfp.repository.impl.UserRepositoryImpl;
import com.example.optionalcoursesfp.service.UserService;
import com.example.optionalcoursesfp.service.impl.UserServiceImpl;
import com.example.optionalcoursesfp.util.connection.ConnectionPool;
import com.example.optionalcoursesfp.util.transaction.TransactionManager;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Test
    public void getUserByLoginAndPasswordTest(){
        TransactionManager transactionManager=mock(TransactionManager.class);
        UserRepository userRepository=new UserRepositoryImpl();
        Connection connection=mock(Connection.class);
        ConnectionPool connectionPool=mock(ConnectionPool.class);
        UserService userService=new UserServiceImpl(userRepository,connectionPool);
        try {
            when(connectionPool.getConnection()).thenReturn(connection);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement=mock(PreparedStatement.class);
        ResultSet resultSet=mock(ResultSet.class);
        try {
            when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getString("login")).thenReturn("login");
            when(resultSet.getString("password")).thenReturn("password");
            when(resultSet.getInt("role_id")).thenReturn(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        User expected=new User("login","password", UserRole.ADMIN);
        User actual = null;
        try {
            actual=userService.getUserByLoginAndPassword("login","password");
        } catch ( SQLQueryException e) {
            e.printStackTrace();
        }
        assertEquals(expected,actual);
    }
    @Test
    public void insertUserTest(){
        TransactionManager transactionManager=mock(TransactionManager.class);
        UserRepository userRepository=new UserRepositoryImpl();
        Connection connection=mock(Connection.class);
        ConnectionPool connectionPool=mock(ConnectionPool.class);
        UserService userService=new UserServiceImpl(userRepository,connectionPool);
        try {
            when(connectionPool.getConnection()).thenReturn(connection);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement=mock(PreparedStatement.class);
        try {
            when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);
            when(preparedStatement.executeUpdate()).thenReturn(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            userService.insertUser("login","password",1,"+380663004233");
        } catch (SQLQueryException | DatabaseException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void isTheUserAlreadyTest(){
        TransactionManager transactionManager=mock(TransactionManager.class);
        UserRepository userRepository=new UserRepositoryImpl();
        Connection connection=mock(Connection.class);
        ConnectionPool connectionPool=mock(ConnectionPool.class);
        UserService userService=new UserServiceImpl(userRepository,connectionPool);
        try {
            when(connectionPool.getConnection()).thenReturn(connection);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        PreparedStatement preparedStatement=mock(PreparedStatement.class);
        ResultSet resultSet=mock(ResultSet.class);
        try {
            when(connection.prepareStatement(isA(String.class))).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getInt("count")).thenReturn(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int actual = 0;
        try {
            actual=userService.isTheUserAlready("login","+380663004233");
        } catch (SQLQueryException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(1,actual);
    }
}
