package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.TeacherService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCourseCommand implements Command {
    private final CourseService courseService;
    private final TeacherService teacherService;

    public DeleteCourseCommand(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }

    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            courseService.deleteCourse(Integer.parseInt(request.getParameter("courseId")));
            new ShowCoursesCommand(courseService, teacherService).executeCommand(request, response);
        } catch (SQLQueryException e) {
            try {
                request.getRequestDispatcher("Error.jsp").forward(request,response);
            } catch (ServletException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
