package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCourseCommand implements Command {
    private final CourseService courseService;

    public DeleteCourseCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            courseService.deleteCourse(Integer.parseInt(request.getParameter("courseId")));
            response.sendRedirect("dispatcher-servlet?pageName=showCourses");
        } catch (SQLQueryException e) {
            e.printStackTrace();
            try {
                request.getRequestDispatcher("Error.jsp").forward(request,response);
            } catch (ServletException | IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
