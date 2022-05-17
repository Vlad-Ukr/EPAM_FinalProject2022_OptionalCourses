package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.entity.User;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.StudentService;
import com.example.optionalcoursesfp.service.TeacherService;
import com.example.optionalcoursesfp.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class LoginUserCommand implements Command {
    private static final Logger log = Logger.getLogger(LoginUserCommand.class);
    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    public LoginUserCommand(UserService userService, StudentService studentService, TeacherService teacherService) {
        this.userService = userService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }
     /*
     This command grants access to the user's role page
     if in database no user with this login or password is incorrect
     than send denied message
     */
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response)  {
        User user = new User();
        try {
            user = userService.getUserByLoginAndPassword(request.getParameter("user_login"), request.getParameter("user_password"));
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
        if (user.getLogin() == null) {
            request.setAttribute("loginError", "Incorrect login or password!");
            try {
                request.getRequestDispatcher("loginPage.jsp").forward(request, response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
            return;
        }
        log.info(user.toString());
        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("userLogin", user.getLogin());
        try {
            response.sendRedirect("dispatcher-servlet?rolePage=" +
                    getAddressToUserRolePage(user, request, response)+"&pageName=instruction" );
       } catch ( IOException e) {
           e.printStackTrace();
        }
    }
    /*
    This method determine user role amn return page address
    */
    private String getAddressToUserRolePage(User user, HttpServletRequest request, HttpServletResponse response) {
        switch (user.getRole()) {
            case ADMIN:
                log.info(user.toString());
                request.getSession().setAttribute("userRole", "admin");
                return "admin.jsp";
            case STUDENT:
                log.info(user.toString());
                try {
                    request.getSession().setAttribute("student", studentService.getStudentByLogin(user.getLogin()));
                } catch (SQLQueryException e) {
                    try {
                        request.getRequestDispatcher("Error.jsp").forward(request,response);
                    } catch (ServletException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
                request.getSession().setAttribute("userRole", "student");
                return "student.jsp";
            case TEACHER:
                log.info(user.toString());
                try {
                    request.getSession().setAttribute("teacher", teacherService.getTeacherByLogin(user.getLogin()));
                } catch (SQLQueryException e) {
                    e.printStackTrace();
                    try {
                        request.getRequestDispatcher("Error.jsp").forward(request,response);
                    } catch (ServletException | IOException ex) {
                        ex.printStackTrace();
                    }
                }
                request.getSession().setAttribute("userRole", "teacher");
                return "teacher.jsp";
        }

        return null;
    }


}
