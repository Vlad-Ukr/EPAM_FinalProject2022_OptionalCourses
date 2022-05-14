package CommandTest;

import com.example.optionalcoursesfp.command.commandimpl.ShowCoursesCommand;
import com.example.optionalcoursesfp.command.commandimpl.ShowCoursesForStudentCommand;
import com.example.optionalcoursesfp.command.commandimpl.SortCourseCommand;
import com.example.optionalcoursesfp.entity.Course;
import com.example.optionalcoursesfp.exeption.DatabaseException;
import com.example.optionalcoursesfp.repository.CourseRepository;
import com.example.optionalcoursesfp.repository.impl.CourseRepositoryImpl;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.TeacherService;
import com.example.optionalcoursesfp.util.connection.ConnectionPool;
import com.example.optionalcoursesfp.util.transaction.TransactionManager;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class SortCourseCommandTest {
    @Test
    public void sortCommandTest(){
        CourseService courseService=mock(CourseService.class);
        TeacherService teacherService=mock(TeacherService.class);
        SortCourseCommand sortCourseCommand=new SortCourseCommand(courseService,teacherService);
        HttpServletRequest request=mock(HttpServletRequest.class);
        HttpServletResponse response=mock(HttpServletResponse.class);
        HttpSession session=mock(HttpSession.class);
        doNothing().when(session).setAttribute(isA(String.class),isA(Object.class));
        doNothing().when(request).setAttribute(isA(String.class),isA(Object.class));
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("studentPageMarker")).thenReturn("true").thenReturn("false");
        when(request.getParameter("sortSelect")).thenReturn("byNameA-Z");
        List<Course> courseList=new ArrayList<>();
        courseList.add(new Course(1,"name1",10,10,10,"topic1",1,"status1","full_name1"));
        courseList.add(new Course(2,"name2",10,10,10,"topic2",2,"status2","full_name2"));
        when(request.getSession().getAttribute("courseListForSorting")).thenReturn(courseList);
        CourseRepository courseRepository=new CourseRepositoryImpl();
        TransactionManager transactionManager = mock(TransactionManager.class);
        Connection connection = mock(Connection.class);
        ConnectionPool connectionPool = mock(ConnectionPool.class);
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
        ShowCoursesForStudentCommand showCoursesForStudentCommand=mock(ShowCoursesForStudentCommand.class);
        ShowCoursesCommand showCoursesCommand=mock(ShowCoursesCommand.class);
        doNothing().when(showCoursesCommand).executeCommand(request,response);
        doNothing().when(showCoursesForStudentCommand).executeCommand(request,response);
        RequestDispatcher requestDispatcher=mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(isA(String.class))).thenReturn(requestDispatcher);
        sortCourseCommand.executeCommand(request,response);
    }
}
