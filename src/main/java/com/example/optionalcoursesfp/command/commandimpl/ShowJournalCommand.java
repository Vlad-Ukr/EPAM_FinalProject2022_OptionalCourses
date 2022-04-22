package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.StudentService;
import com.example.optionalcoursesfp.service.TeacherService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowJournalCommand implements Command {
    private final StudentService studentService;
    private final CourseService courseService;
    private static final Logger log = Logger.getLogger(ShowJournalCommand.class);

    public ShowJournalCommand(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }
    /*
    This command get from DB list of student by id of selected course
    and load to session  and forward to teacher page
     */
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            if(request.getParameter("courseStatus").equals("Закончен")){
                request.getSession().setAttribute("finishedCourses",studentService.getStudentsFromFinishedCourses(Integer.parseInt(request.getParameter("courseId"))));;
            }
            else {
                request.getSession().setAttribute("students", studentService.getStudentsByCourseId(Integer.parseInt(request.getParameter("courseId"))));
            }
            log.info(studentService.getStudentsByCourseId(Integer.parseInt(request.getParameter("courseId"))));
            request.setAttribute("pageName", "showJournal");
            request.setAttribute("courseStatus", request.getParameter("courseStatus"));
            request.setAttribute("course", courseService.getCourseById(Integer.parseInt(request.getParameter("courseId"))));
            request.getRequestDispatcher("teacher.jsp").forward(request, response);
        } catch (SQLQueryException | ServletException | IOException e) {
            try {
                request.getRequestDispatcher("Error.jsp").forward(request,response);
            } catch (ServletException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
