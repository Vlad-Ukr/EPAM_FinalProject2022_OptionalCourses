package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.entity.Course;
import com.example.optionalcoursesfp.entity.Student;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.StudentService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ShowStudentCommand implements Command {
    private  static  final Logger logger=Logger.getLogger(ShowStudentCommand.class);
    private final StudentService studentService;
    private final CourseService courseService;

    public ShowStudentCommand(StudentService studentService,CourseService courseService) {
        this.studentService = studentService;
        this.courseService=courseService;
    }
    /*
    This command get from DB list of students when there is no list in the request
    and get course list form DB
    after that forward to admin page
     */
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Student> studentList;
            if(request.getAttribute("listAlreadyIs")!=null){
                 studentList=(List<Student>)request.getAttribute("studentList");
                logger.info(studentList.toString());
            }else {
                studentList=studentService.getAllStudent();
                logger.info(studentList.toString());
            }
            logger.info(studentList.toString());
            List<Course> courseList = courseService.getAllCourses();
            request.getSession().setAttribute("studentList",studentList);
            request.setAttribute("studentList",studentList);
            request.setAttribute("courseList",courseList);
            request.setAttribute("pageName","showStudents");
            request.getRequestDispatcher("admin.jsp").forward(request,response);
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
