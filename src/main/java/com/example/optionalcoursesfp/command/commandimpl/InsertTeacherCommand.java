package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.entity.UserRole;
import com.example.optionalcoursesfp.exeption.DatabaseException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.TeacherService;
import com.example.optionalcoursesfp.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class InsertTeacherCommand implements Command {
    private static final Logger log = Logger.getLogger(InsertTeacherCommand.class);
    private final UserService userService;
    private final TeacherService teacherService;
    private final CourseService courseService;

    public InsertTeacherCommand(UserService userService, TeacherService teacherService, CourseService courseService) {
        this.userService = userService;
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    /*
            This command inserts new teacher
            */
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (userService.isTheUserAlready(request.getParameter("user_email"), request.getParameter("phone_number")) == 0) {
                userService.insertUser(request.getParameter("user_email"), request.getParameter("user_password"), UserRole.determineUserRoleNumber(UserRole.TEACHER), request.getParameter("phone_number"));
            } else {
                log.info("teacher- " + request.getParameter("user_email") + "wasn't added");
                response.sendRedirect("dispatcher-servlet?pageName=showTeachers&deniedMessage=message");
                return;
            }
            log.info(request.getParameter("user_fullname"));
            teacherService.addTeacher(request.getParameter("user_email")
                    , request.getParameter("user_password"), request.getParameter("user_fullname"));
            log.info("teacher- " + request.getParameter("user_email") + "was added successfully");
           response.sendRedirect("dispatcher-servlet?pageName=showTeachers&successMessage=message");
        } catch (DatabaseException | SQLQueryException e) {
            try {
                request.getRequestDispatcher("Error.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
