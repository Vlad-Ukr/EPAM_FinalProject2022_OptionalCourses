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

public class SelectCoursesCommand implements Command {
    private final CourseService courseService;
    private final TeacherService teacherService;
    private static final Logger log = Logger.getLogger(SelectCoursesCommand.class);
    public SelectCoursesCommand(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }
    /*
    This command get selecting terms
    and command get list of courses from DB
     */
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info(Integer.parseInt(request.getParameter("teacherSelect")));
            List<Course> courseList=selectingCourses(Integer.parseInt(request.getParameter("teacherSelect")),request.getParameter("topicSelect"));
            request.setAttribute("courseList", courseList);
            request.setAttribute("showAllCourses","false");
            request.getSession().setAttribute("courseListForSorting", courseList);
            new ShowCoursesForStudentCommand(courseService, teacherService).executeCommand(request, response);
        } catch (SQLQueryException e) {
            try {
                request.getRequestDispatcher("Error.jsp").forward(request,response);
            } catch (ServletException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    private List<Course> selectingCourses(int teacherId,String topic) throws SQLQueryException {
       if(teacherId==0&&topic.equals("allTopics")){
           return courseService.getAllCourses();
       }else if(teacherId==0){
           return courseService.getCoursesByTopic(topic);
       }else if(topic.equals("allTopics")){
           return courseService.getCoursesByTeacher(teacherId);
       }else {
           return courseService.getCoursesByTopicAndTeacher(teacherId,topic);
       }
    }
}
