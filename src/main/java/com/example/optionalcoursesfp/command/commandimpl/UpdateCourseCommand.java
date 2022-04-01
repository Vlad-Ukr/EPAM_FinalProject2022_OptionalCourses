package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.exeption.CourseAlreadyExistException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.TeacherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateCourseCommand implements Command {
    private final CourseService courseService;
    private final TeacherService teacherService;

    public UpdateCourseCommand(CourseService courseService, TeacherService teacherService) {
        this.courseService = courseService;
        this.teacherService = teacherService;
    }
/*
This command update course
if already is course with same name
than command send denied message
 */
    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            courseService.updateCourse(Integer.parseInt(request.getParameter("courseId")),request.getParameter("courseName")
                    ,Integer.parseInt(request.getParameter("courseDuration")),Integer.parseInt(request.getParameter("courseMaxAmountOfStudent")),request.getParameter("courseTopic"));
            request.setAttribute("registerMessage", "Курс упешно обновлен!");
            new ShowCoursesCommand(courseService,teacherService).executeCommand(request,response);
        } catch (SQLQueryException e) {
            e.printStackTrace();
        } catch (CourseAlreadyExistException e) {
            request.setAttribute("deniedRegister", "Курс с таким названием уже существует");
            new ShowCoursesCommand(courseService,teacherService).executeCommand(request,response);
        }
    }
}
