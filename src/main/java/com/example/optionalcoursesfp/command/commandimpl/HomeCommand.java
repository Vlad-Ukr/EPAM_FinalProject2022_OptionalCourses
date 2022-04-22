package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.entity.Student;
import com.example.optionalcoursesfp.entity.Teacher;
import com.example.optionalcoursesfp.entity.User;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.CourseService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeCommand implements Command {
    private final CourseService courseService;
    private static final Logger log = Logger.getLogger(HomeCommand.class);

    public HomeCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    /*
     This command forward user to his home page
     */
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        try {
            if (user == null) {
                request.getRequestDispatcher("securityError.jsp").forward(request, response);
            }
            forwardToUserHomePage(user, request, response);
        } catch (ServletException | IOException | SQLQueryException e) {
            try {
                request.getRequestDispatcher("Error.jsp").forward(request,response);
            } catch (ServletException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /*
    This method determine role of user
    and forwarding to home page
     */
    private void forwardToUserHomePage(User user, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLQueryException {
        switch (user.getRole()) {
            case STUDENT:
                request.setAttribute("pageName", "showHome");
                request.setAttribute("studentCourses", courseService.getStudentRegisteredCourses((Student) request.getSession().getAttribute("student")));
                request.setAttribute("finishedStudentCourses", courseService.getStudentFinishedCourses(((Student) request.getSession().getAttribute("student")).getId()));
                log.info(courseService.getStudentRegisteredCourses((Student) request.getSession().getAttribute("student")));
                request.getRequestDispatcher("student.jsp").forward(request, response);
                break;
            case TEACHER:
                log.info(user);
                request.setAttribute("pageName", "showHome");
                Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
                log.info(teacher);
                request.setAttribute("teacherCourses", courseService.getCoursesByTeacher(teacher.getId()));
                request.getRequestDispatcher("teacher.jsp").forward(request, response);
                break;

        }
    }
}
