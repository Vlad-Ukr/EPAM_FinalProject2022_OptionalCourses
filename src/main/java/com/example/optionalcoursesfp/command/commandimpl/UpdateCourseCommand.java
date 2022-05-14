package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.exeption.CourseAlreadyExistException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.TeacherService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateCourseCommand implements Command {
    private final CourseService courseService;
    private final TeacherService teacherService;

    public UpdateCourseCommand(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }
/*
This command update course
if already is course with same name
than command send denied message
 */
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            courseService.updateCourse(Integer.parseInt(request.getParameter("courseId")),request.getParameter("courseName")
                    ,Integer.parseInt(request.getParameter("courseDuration")),Integer.parseInt(request.getParameter("courseMaxAmountOfStudent")),request.getParameter("courseTopic"));
            request.setAttribute("updateMessage", "Курс упешно обновлен!");
            response.sendRedirect("dispatcher-servlet?pageName=showCourses&successMessage=message");
        } catch (SQLQueryException e) {
            try {
                request.getRequestDispatcher("Error.jsp").forward(request,response);
            } catch (ServletException | IOException ex) {
                ex.printStackTrace();
            }
        } catch (CourseAlreadyExistException e) {
            request.setAttribute("deniedRegister", "Курс с таким названием уже существует");
            try {
                response.sendRedirect("dispatcher-servlet?pageName=showCourses&deniedMessage=message");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
