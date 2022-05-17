package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.StudentService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SelectStudentCommand implements Command {
    private final StudentService studentService;
    private final CourseService courseService;
    private  static  final Logger logger=Logger.getLogger(SelectStudentCommand.class);
    public SelectStudentCommand(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }
    /*
    This command get list of student by selected course and send to jsp
     */
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            String string=request.getParameter("courseSelect");
            logger.info(string);
            if (request.getParameter("courseSelect").equals("allCourses")) {
                new ShowStudentCommand(studentService, courseService).executeCommand(request, response);
                return;
            }
            else {
                request.setAttribute("studentList",studentService.getStudentsByCourseId(Integer.parseInt(request.getParameter("courseSelect"))));
                request.setAttribute("listAlreadyIs","true");
                logger.info(studentService.getStudentsByCourseId(Integer.parseInt(request.getParameter("courseSelect"))));
            }
        } catch (SQLQueryException e) {
            e.printStackTrace();
            try {
                request.getRequestDispatcher("Error.jsp").forward(request,response);
            } catch (ServletException | IOException ex) {
                ex.printStackTrace();
            }
        }
        new ShowStudentCommand(studentService,courseService).executeCommand(request,response);
    }
}
