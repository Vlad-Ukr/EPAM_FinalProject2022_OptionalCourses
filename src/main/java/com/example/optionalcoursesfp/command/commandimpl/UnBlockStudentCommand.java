package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.StudentService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnBlockStudentCommand  implements Command {
    private static final Logger log = Logger.getLogger(UnBlockStudentCommand.class);
    private final StudentService studentService;
    private final CourseService courseService;

    public UnBlockStudentCommand(StudentService studentService,CourseService courseService) {
        this.studentService = studentService;
        this.courseService=courseService;
    }
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            studentService.unBlockStudent(request.getParameter("studentLogin"));
            new ShowStudentCommand(studentService,courseService).executeCommand(request,response);
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
    }
}
