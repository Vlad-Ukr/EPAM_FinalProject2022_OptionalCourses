package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.entity.Course;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.TeacherService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ShowCoursesForStudentCommand implements Command {
    private final CourseService courseService;
    private final TeacherService teacherService;
    private static final Logger log = Logger.getLogger(ShowCoursesForStudentCommand.class);

    public ShowCoursesForStudentCommand(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }
    /*
    This command send list of courses to student page
     */
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {

        try {
            log.info(request.getAttribute("showAllCourses"));
            if (request.getAttribute("showAllCourses")==null||request.getAttribute("showAllCourses").equals("true")) {
                log.info(request.getSession().getAttribute("courseList"));
                List<Course> courseList = courseService.getAllCourses();
                List<String> topicList = courseList.stream().map(Course::getTopic).collect(Collectors.toList());
                request.setAttribute("courseList", courseList);
                request.getSession().setAttribute("topicList", topicList);
                request.getSession().setAttribute("teacherList", teacherService.getAllTeachers());
                request.getSession().setAttribute("courseListForSorting", courseList);
                request.setAttribute("pageName", "showCourses");
                log.info(courseList);
                request.getRequestDispatcher("student.jsp").forward(request, response);

            } else {
                log.info(request.getAttribute("courseList"));
                request.setAttribute("courseList", request.getAttribute("courseList"));
                request.setAttribute("pageName", "showCourses");
                request.getRequestDispatcher("student.jsp").forward(request, response);
            }
        } catch (SQLQueryException e) {
            e.printStackTrace();
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
