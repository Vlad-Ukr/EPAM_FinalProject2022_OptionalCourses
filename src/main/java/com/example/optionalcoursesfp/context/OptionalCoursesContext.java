package com.example.optionalcoursesfp.context;


import com.example.optionalcoursesfp.command.commandcontroller.CommandController;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.StudentService;
import com.example.optionalcoursesfp.service.TeacherService;
import com.example.optionalcoursesfp.service.UserService;

public class OptionalCoursesContext {
    UserService userService;
    StudentService studentService;
    TeacherService teacherService;
    CourseService  courseService;
    CommandController commandController;

    public OptionalCoursesContext(UserService userService, StudentService studentService, TeacherService teacherService, CourseService courseService, CommandController commandController) {
        this.userService = userService;
        this.studentService = studentService;
        this.commandController = commandController;
        this.teacherService=teacherService;
        this.courseService=courseService;
    }

    public UserService getUserService() {
        return userService;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public CommandController getCommandController() {
        return commandController;
    }

    public TeacherService getTeacherService() {
        return teacherService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    @Override
    public String toString() {
        return "OptionalCoursesContext{" +
                "userService=" + userService +
                ", studentService=" + studentService +
                ", teacherService=" + teacherService +
                ", courseService=" + courseService +
                ", commandController=" + commandController +
                '}';
    }
}
