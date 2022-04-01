package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.entity.Student;
import com.example.optionalcoursesfp.exeption.BlockedStudentException;
import com.example.optionalcoursesfp.exeption.MaxAmountOfRegistrationException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.exeption.StudentAlreadyRegisteredException;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.StudentService;
import com.example.optionalcoursesfp.service.TeacherService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterStudentOnCourseCommand implements Command {
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    private static final Logger log = Logger.getLogger(RegisterStudentOnCourseCommand.class);
    public RegisterStudentOnCourseCommand(CourseService courseService, TeacherService teacherService, StudentService studentService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }
      /*
      This command get student from session and  register  on selected course
      if amount of free places on course is zero,and course is in process or ended
      than send denied message
      */
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            Student student=(Student)request.getSession().getAttribute("student");
            log.info(student);
            if((Integer.parseInt(request.getParameter("courseAmountOfStudent"))-Integer.parseInt(request.getParameter("courseMaxAmountOfStudent")))==0){
                request.setAttribute("maxAmountOfStudent", "На этом курсе больше нет мест!");
                new ShowCoursesForStudentCommand(courseService,teacherService).executeCommand(request,response);
                return;
            }
            else if(request.getParameter("courseStatus")!=null&&request.getParameter("courseStatus").equals("Закончен")) {
                request.setAttribute("endCourse", "Этот курс уже закончен!");
                new ShowCoursesForStudentCommand(courseService,teacherService).executeCommand(request,response);
                return;
            }else if(request.getParameter("courseStatus")!=null&&request.getParameter("courseStatus").equals("В процессе")){
                request.setAttribute("goingCourse", "Набор на этот курс уже закончен!");
                new ShowCoursesForStudentCommand(courseService,teacherService).executeCommand(request,response);
                return;
            }
            studentService.registerStudentOnCourse(student,Integer.parseInt(request.getParameter("courseId")));
            request.getSession().setAttribute("student",student);
            log.info(request.getSession().getAttribute("student"));
        } catch (SQLQueryException e) {
            e.printStackTrace();
        }catch (StudentAlreadyRegisteredException e){
            request.setAttribute("studentAlreadyRegistered", "Вы уже зарегестрированы на этот курс!");
        }catch (MaxAmountOfRegistrationException e){
            request.setAttribute("maxAmountOfRegistration", "Вы уже зарегестрированы на максимольное количество курсов(3)!");
        }catch (BlockedStudentException e){
            request.setAttribute("blockedStudent", "Вы не можете зарегестрироваться!" +
                    "\n Вы заблокированы! ");
        }
        request.setAttribute("accessedRegistration", "Вы успешно записались на курс!");
        new ShowCoursesForStudentCommand(courseService,teacherService).executeCommand(request,response);
    }
}
