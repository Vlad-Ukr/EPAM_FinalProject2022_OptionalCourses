package com.example.optionalcoursesfp;


import com.example.optionalcoursesfp.command.commandcontroller.CommandController;
import com.example.optionalcoursesfp.context.OptionalCoursesContext;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.StudentService;
import com.example.optionalcoursesfp.service.TeacherService;
import com.example.optionalcoursesfp.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "dispatcherServlet", value = "/dispatcher-servlet")
public class DispatcherServlet extends HttpServlet {
    private  static  final Logger log=Logger.getLogger(DispatcherServlet.class);
    private UserService userService;
    private StudentService studentService;
    private TeacherService teacherService;
    private CourseService courseService;
    private CommandController commandController;

    public void init() {
        ServletContext context = getServletContext();
        OptionalCoursesContext optionalCoursesContext = (OptionalCoursesContext) context.getAttribute("OptionalCoursesContext");
        System.out.println(optionalCoursesContext);
        userService = optionalCoursesContext.getUserService();
        studentService = optionalCoursesContext.getStudentService();
        teacherService=optionalCoursesContext.getTeacherService();
        courseService=optionalCoursesContext.getCourseService();
        commandController = optionalCoursesContext.getCommandController();
        log.info("Servlet was initialized");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info(request.getParameter("pageName")+" request parameter");
        request.getSession().setAttribute("pageName",request.getParameter("pageName"));
        commandController.invoke(request.getParameter("pageName"), request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         request.setCharacterEncoding("UTF-8");
       response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        log.info(request.getParameter("pageName")+" request parameter");
        request.getSession().setAttribute("pageName",request.getParameter("pageName"));
       commandController.invoke(request.getParameter("pageName"), request, response);
    }


    public void destroy() {
    }


}