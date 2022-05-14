package CommandTest;

import com.example.optionalcoursesfp.command.commandimpl.HomeCommand;
import com.example.optionalcoursesfp.entity.Teacher;
import com.example.optionalcoursesfp.entity.User;
import com.example.optionalcoursesfp.entity.UserRole;
import com.example.optionalcoursesfp.service.CourseService;
import org.junit.jupiter.api.Test;


import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import static org.mockito.Mockito.*;

public class HomeCommandTest {
    @Test
    public void HomeCommandTest() {
        CourseService courseService = mock(CourseService.class);
        HomeCommand homeCommand = new HomeCommand(courseService);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        doNothing().when(session).setAttribute(isA(String.class), isA(Object.class));
        doNothing().when(request).setAttribute(isA(String.class), isA(Object.class));
        when(request.getSession()).thenReturn(session);
        User user = new User(1, "login", "password", "+380663004233", "test.png", UserRole.TEACHER);
        Teacher teacher = new Teacher("fullname");
        when(request.getSession().getAttribute("user")).thenReturn(user);
        when(request.getSession().getAttribute("user")).thenReturn(user);
        when(request.getSession().getAttribute("base64Encoded")).thenReturn(null);
        when(request.getSession().getAttribute("teacher")).thenReturn(teacher);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(isA(String.class))).thenReturn(requestDispatcher);
        homeCommand.executeCommand(request, response);
    }

}
