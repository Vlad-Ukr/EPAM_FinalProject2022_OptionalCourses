package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.entity.Course;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.TeacherService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class SortCourseCommand implements Command {
    private final CourseService courseService;
    private final TeacherService teacherService;
    public SortCourseCommand(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }
      /*
      This command  get course from session and sort by selected term
       */
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getParameter("studentPageMarker").equals("true")) {
                List<Course> courseList = sortCourseList((List<Course>) request.getSession().getAttribute("courseListForSorting"), request.getParameter("sortSelect"));
                request.setAttribute("courseList", courseList);
                request.setAttribute("showAllCourses","false");
                new ShowCoursesForStudentCommand(courseService, teacherService).executeCommand(request, response);
            } else {
                List<Course> courseList = sortCourseList(courseService.getAllCourses(), request.getParameter("sortSelect"));
                request.setAttribute("courseList", courseList);
                new ShowCoursesCommand(courseService, teacherService).executeCommand(request, response);
            }
        } catch (SQLQueryException e) {
            e.printStackTrace();
            try {
                request.getRequestDispatcher("Error.jsp").forward(request,response);
            } catch (ServletException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private List<Course> sortCourseList(List<Course> courseList, String sortType) {
        switch (sortType) {
            case "byNameA-Z":
                courseList.sort(Comparator.comparing(Course::getName));
                return courseList;
            case "byNameZ-A":
                courseList.sort(Comparator.comparing(Course::getName).reversed());
                return courseList;
            case "byDuration":
                courseList.sort(Comparator.comparing(Course::getDuration));
                return courseList;
            case "byStudents":
                courseList.sort(Comparator.comparing(Course::getAmountOfStudent));
                return courseList;
            default:
                return courseList;
        }
    }
}
