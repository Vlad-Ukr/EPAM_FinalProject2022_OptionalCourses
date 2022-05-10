package CommandTest;

import com.example.optionalcoursesfp.command.commandimpl.LoginUserCommand;
import com.example.optionalcoursesfp.entity.Teacher;
import com.example.optionalcoursesfp.entity.User;
import com.example.optionalcoursesfp.entity.UserRole;
import com.example.optionalcoursesfp.exeption.DatabaseException;
import com.example.optionalcoursesfp.service.StudentService;
import com.example.optionalcoursesfp.service.TeacherService;
import com.example.optionalcoursesfp.service.UserService;
import com.example.optionalcoursesfp.util.connection.ConnectionPool;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
public class LoginUserCommandTest {
    @Test
    public void loginCommandTest() {
        UserService userService=mock(UserService.class);
        StudentService studentService=mock(StudentService.class);
        TeacherService teacherService=mock(TeacherService.class);
        LoginUserCommand loginUserCommand =new LoginUserCommand(userService,studentService,teacherService);
        HttpServletRequest request=mock(HttpServletRequest.class);
        HttpServletResponse response=mock(HttpServletResponse.class);
        HttpSession session=mock(HttpSession.class);
        doNothing().when(session).setAttribute(isA(String.class),isA(Object.class));
        doNothing().when(request).setAttribute(isA(String.class),isA(Object.class));
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("user_login")).thenReturn("user_login");
        when(request.getParameter("user_password")).thenReturn("user_password");
        User testUser=new User("user_login","user_password", UserRole.ADMIN);
        Connection connection=mock(Connection.class);
        ConnectionPool connectionPool=mock(ConnectionPool.class);
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
            when(resultSet.getString("login")).thenReturn(testUser.getLogin());
            when(resultSet.getString("password")).thenReturn(testUser.getPassword());
            when(resultSet.getInt("role_id")).thenReturn(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            when(userService.getUserByLoginAndPassword("user_login","user_password")).thenReturn(testUser);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        RequestDispatcher requestDispatcher=mock(RequestDispatcher.class);
         when(request.getRequestDispatcher(isA(String.class))).thenReturn(requestDispatcher);
        loginUserCommand.executeCommand(request,response);

    }
}
