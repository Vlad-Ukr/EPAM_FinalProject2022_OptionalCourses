package com.example.optionalcoursesfp.command.commandcontroller;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.command.commandimpl.*;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.StudentService;
import com.example.optionalcoursesfp.service.TeacherService;
import com.example.optionalcoursesfp.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class CommandController {
    private final HashMap<String, Command> commandHashMap = new HashMap();

    public CommandController(UserService userService, StudentService studentService, TeacherService teacherService, CourseService courseService) {
        commandHashMap.put("loginUser", new LoginUserCommand(userService,studentService,teacherService));
        commandHashMap.put("insertUser", new InsertStudentCommand(studentService,userService));
        commandHashMap.put("logOut", new LogOutCommand());
        commandHashMap.put("showStudents", new ShowStudentCommand(studentService,courseService));
        commandHashMap.put("unBlock", new UnBlockStudentCommand(studentService,courseService));
        commandHashMap.put("block", new BlockStudentCommand(studentService,courseService));
        commandHashMap.put("showTeachers", new ShowTeacherCommand(teacherService,courseService));
        commandHashMap.put("insertTeacher", new InsertTeacherCommand(userService,teacherService,courseService));
        commandHashMap.put("showCourses", new ShowCoursesCommand(courseService, teacherService));
        commandHashMap.put("insertCourse", new InsertCourseCommand(courseService, teacherService));
        commandHashMap.put("deleteCourse", new DeleteCourseCommand(courseService,teacherService));
        commandHashMap.put("updateCourse", new UpdateCourseCommand(courseService,teacherService));
        commandHashMap.put("sortCourse", new SortCourseCommand(courseService,teacherService));
        commandHashMap.put("showCoursesStudent",new ShowCoursesForStudentCommand(courseService,teacherService));
        commandHashMap.put("selectCourses",new SelectCoursesCommand(courseService,teacherService));
        commandHashMap.put("homePage",new HomeCommand(courseService));
        commandHashMap.put("registerOnCourse",new RegisterStudentOnCourseCommand(courseService,teacherService,studentService));
        commandHashMap.put("showJournal",new ShowJournalCommand(studentService,courseService));
        commandHashMap.put("gradingStudents",new GradingCommand(studentService,courseService,teacherService));
        commandHashMap.put("startCourse",new StartCourseCommand(courseService));
        commandHashMap.put("selectStudents",new SelectStudentCommand(studentService,courseService));
        commandHashMap.put("endCourse",new EndCourse(studentService,courseService,teacherService));
        commandHashMap.put("loadAvatar",new UploadAvatarCommand(userService,courseService));
    }

    public void invoke(String command, HttpServletRequest request, HttpServletResponse response) {
        commandHashMap.get(command).executeCommand(request, response);
    }
}
