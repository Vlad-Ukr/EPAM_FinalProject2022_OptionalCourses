package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.entity.Student;
import com.example.optionalcoursesfp.exeption.MaxAmountOfRegistrationException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.exeption.StudentAlreadyRegisteredException;
import com.example.optionalcoursesfp.service.StudentService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterStudentOnCourseCommand implements Command {
    private final StudentService studentService;
    private static final Logger log = Logger.getLogger(RegisterStudentOnCourseCommand.class);

    public RegisterStudentOnCourseCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    /*
    This command get student from session and  register  on selected course
    if amount of free places on course is zero,and course is in process or ended
    than send denied message
    */
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            Student student = (Student) request.getSession().getAttribute("student");
            log.info(student);
            log.info(request.getParameter("courseStatus"));
            if ((Integer.parseInt(request.getParameter("courseAmountOfStudent")) - Integer.parseInt(request.getParameter("courseMaxAmountOfStudent"))) == 0) {
                request.setAttribute("maxAmountOfStudent", "No such places on course!");
                response.sendRedirect("dispatcher-servlet?pageName=showCoursesStudent&deniedMessage=maxAmountOfStudent");
                return;
            } else if (request.getParameter("courseStatus") != null && request.getParameter("courseStatus").equals("Ended")) {
                request.setAttribute("endCourse", "Course is end!");
                response.sendRedirect("dispatcher-servlet?pageName=showCoursesStudent&deniedMessage=endCourse");
                return;
            } else if (request.getParameter("courseStatus") != null && request.getParameter("courseStatus").equals("In process")) {
                request.setAttribute("goingCourse", "Registration on this course is over!");
                response.sendRedirect("dispatcher-servlet?pageName=showCoursesStudent&deniedMessage=goingCourse");
                return;
            } else if (student.getStatus().equals("Blocked")) {
                request.setAttribute("blockedStudent", "You can not register!" +
                        "\n You are blocked! ");
                response.sendRedirect("dispatcher-servlet?pageName=showCoursesStudent&deniedMessage=blockedStudent");
                return;
            }
            studentService.registerStudentOnCourse(student, Integer.parseInt(request.getParameter("courseId")));
            request.getSession().setAttribute("student", student);
            log.info(request.getSession().getAttribute("student"));
        } catch (SQLQueryException e) {
            e.printStackTrace();
            try {
                request.getRequestDispatcher("Error.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                ex.printStackTrace();
            }
        } catch (StudentAlreadyRegisteredException e) {
            try {
                response.sendRedirect("dispatcher-servlet?pageName=showCoursesStudent&deniedMessage=studentAlreadyRegistered");
                return;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            request.setAttribute("studentAlreadyRegistered", "You are already registered!");
        } catch (MaxAmountOfRegistrationException e) {
            try {
                response.sendRedirect("dispatcher-servlet?pageName=showCoursesStudent&deniedMessage=maxAmountOfRegistration");
                return;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            request.setAttribute("maxAmountOfRegistration", "You have maximum registrations(3)!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            response.sendRedirect("dispatcher-servlet?pageName=showCoursesStudent&successMessage=message");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

