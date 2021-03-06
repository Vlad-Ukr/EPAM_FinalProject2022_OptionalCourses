package com.example.optionalcoursesfp.command.commandimpl;


import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.entity.UserRole;
import com.example.optionalcoursesfp.exeption.DatabaseException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.StudentService;
import com.example.optionalcoursesfp.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class InsertStudentCommand implements Command {
    private static final Logger log = Logger.getLogger(InsertStudentCommand.class);
    private final StudentService studentService;
    private final UserService userService;

    public InsertStudentCommand(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    /*
        This command inserts new student
        */
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (userService.isTheUserAlready(request.getParameter("user_email"), request.getParameter("phone_number")) == 0) {
                userService.insertUser(request.getParameter("user_email")
                        , request.getParameter("user_password"), UserRole.determineUserRoleNumber(UserRole.STUDENT), request.getParameter("phone_number"));
            } else {
                request.getSession().setAttribute("deniedRegister", "Пользователь с таким логином уже существует!");
                response.sendRedirect("registerPage.jsp");
                return;
            }
            studentService.insertStudent(request.getParameter("user_email")
                    , request.getParameter("user_password"), request.getParameter("user_fullname"));
            log.info("student- " + request.getParameter("user_email") + "was added successfully");
            request.getSession().setAttribute("registerMessage", "Регистрация прошла упешно!");
            response.sendRedirect("loginPage.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLQueryException | DatabaseException throwables) {
            try {
                request.getRequestDispatcher("Error.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
