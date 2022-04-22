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

public class InsertCourseCommand implements Command {
    private final CourseService courseService;
    private final TeacherService teacherService;

    public InsertCourseCommand(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }
    /*
    This command inserts new course
    */
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            courseService.addCourse(request.getParameter("courseName"),Integer.parseInt(request.getParameter("courseDuration"))
                    ,Integer.parseInt(request.getParameter("maxAmount")),request.getParameter("topic")
                    ,Integer.parseInt(request.getParameter("teacherSelect")));
            request.setAttribute("registerMessage", "Курс добавлен упешно!");
            request.setAttribute("teacherList",teacherService.getAllTeachers());
            request.setAttribute("courseList",courseService.getAllCourses());
            request.setAttribute("pageName","showCourses");
            try {
                request.getRequestDispatcher("admin.jsp").forward(request,response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        }catch (SQLQueryException | CourseAlreadyExistException exception){
            request.setAttribute("deniedRegister", "Курс с таким названием уже существует!");
            try {
                request.setAttribute("courseList",courseService.getAllCourses());
                request.setAttribute("teacherList",teacherService.getAllTeachers());
                request.setAttribute("pageName","showCourses");
                request.getRequestDispatcher("admin.jsp").forward(request,response);
            } catch (ServletException | IOException e) {
               e.printStackTrace();
            } catch (SQLQueryException throwables) {
                try {
                    request.getRequestDispatcher("Error.jsp").forward(request,response);
                } catch (ServletException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
