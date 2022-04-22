package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.entity.Course;
import com.example.optionalcoursesfp.entity.Student;
import com.example.optionalcoursesfp.entity.Teacher;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.StudentService;
import com.example.optionalcoursesfp.service.TeacherService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowTeacherCommand implements Command {
    private final TeacherService teacherService;
    private final CourseService courseService;
    private static final Logger logger = Logger.getLogger(ShowTeacherCommand.class);

    public ShowTeacherCommand(TeacherService teacherService, CourseService courseService) {

        this.teacherService = teacherService;
        this.courseService = courseService;
    }
     /*
     This command get list of teachers and list of coursers from DB
     and send to admin page
      */
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Teacher> teacherList = teacherService.getAllTeachers();
            List<Course> courseList = courseService.getAllCourses();
                    logger.info(teacherList.toString());
            request.getSession().setAttribute("teacherList", teacherList);
            request.setAttribute("teacherList", teacherList);
            request.setAttribute("courseList",courseList);
            request.setAttribute("pageName", "showTeachers");
            request.getRequestDispatcher("admin.jsp").forward(request, response);
        } catch (SQLQueryException e) {
            try {
                request.getRequestDispatcher("Error.jsp").forward(request,response);
            } catch (ServletException | IOException ex) {
                ex.printStackTrace();
            }
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
