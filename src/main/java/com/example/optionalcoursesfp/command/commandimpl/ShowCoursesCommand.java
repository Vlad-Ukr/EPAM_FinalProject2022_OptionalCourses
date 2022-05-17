package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.entity.Teacher;
import com.example.optionalcoursesfp.entity.User;
import com.example.optionalcoursesfp.entity.UserRole;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.TeacherService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowCoursesCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowCoursesCommand.class);
    private final CourseService courseService;
    private final TeacherService teacherService;

    public ShowCoursesCommand(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }
    /*
    THis command get list of courses from DB and
    send  to user page
     */
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            User user= (User) request.getSession().getAttribute("user");
            if(user==null){
                response.sendRedirect("loginPage.jsp");
                return;
            }
            if (user.getRole()== UserRole.TEACHER) {
                Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
                request.setAttribute("courseList", courseService.getCoursesByTeacher(teacher.getId()));
                request.setAttribute("pageName", "showCourses");
                if (request.getParameter("endMessage") != null) {
                    request.setAttribute("endMessage", "Course end! Ratings Shown!");
                }
                request.getRequestDispatcher("teacher.jsp").forward(request, response);
                return;
            }
            if (request.getAttribute("courseList") == null) {
                log.info("show courses with request");
                request.setAttribute("courseList", courseService.getAllCourses());
                request.setAttribute("teacherList", teacherService.getAllTeachers());
                request.setAttribute("pageName", "showCourses");
                request.getRequestDispatcher("admin.jsp").forward(request, response);
                return;
            }
            request.setAttribute("courseList", request.getAttribute("courseList"));
            request.setAttribute("teacherList", teacherService.getAllTeachers());
            request.setAttribute("pageName", "showCourses");
            request.getRequestDispatcher("admin.jsp").forward(request, response);
        } catch (SQLQueryException | ServletException | IOException e) {
            e.printStackTrace();
            try {
                request.getRequestDispatcher("Error.jsp").forward(request,response);
            } catch (ServletException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
