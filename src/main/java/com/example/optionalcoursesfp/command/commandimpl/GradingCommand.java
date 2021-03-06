package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.entity.Student;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.StudentService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GradingCommand implements Command {
    private final StudentService studentService;
    private static final Logger log = Logger.getLogger(GradingCommand.class);
    private final int FIRST_COURSE = 1;
    private final int SECOND_COURSE = 2;
    private final int THIRD_COURSE = 3;
    private final String FIRST_COURSE_REQUEST_NAME = "firstCourseMark";
    private final String SECOND_COURSE_REQUEST_NAME = "secondCourseMark";
    private final String THIRD_COURSE_REQUEST_NAME = "thirdCourseMark";

    public GradingCommand(StudentService studentService) {
        this.studentService = studentService;
    }
    /*
This command getting list of students from session,
grading students
and set student mark into DB
*/
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        List<Student> studentList = (List<Student>) request.getSession().getAttribute("students");
        try {
            log.info(request.getParameter("endCourseMarker"));
            for (Student student : studentList) {
                if (request.getParameter("firstCourseMark" + student.getId()) != null) {
                    log.info(student + "grading firstCourseMark");
                    gradeStudent(student, FIRST_COURSE, FIRST_COURSE_REQUEST_NAME, request);

                } else if (request.getParameter("secondCourseMark" + student.getId()) != null) {
                    log.info(student + "grading secondCourseMark");
                    gradeStudent(student, SECOND_COURSE, SECOND_COURSE_REQUEST_NAME, request);

                } else if (request.getParameter("thirdCourseMark" + student.getId()) != null) {
                    gradeStudent(student, THIRD_COURSE, THIRD_COURSE_REQUEST_NAME, request);
                    log.info(student + "grading thirdCourseMark");
                }
            }
            response.sendRedirect("dispatcher-servlet?pageName=showCourses&successMessage=marks");
        } catch (SQLQueryException e) {
            e.printStackTrace();
            try {
                request.getRequestDispatcher("Error.jsp").forward(request,response);
            } catch (ServletException | IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void gradeStudent(Student student, int courseNumber, String courseMark, HttpServletRequest request) throws SQLQueryException {
        studentService.grateStudent(student, courseNumber, Integer.parseInt(request.getParameter(courseMark + student.getId())));
    }

}
