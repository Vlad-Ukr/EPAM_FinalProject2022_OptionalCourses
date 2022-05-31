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
    private static final Logger log = Logger.getLogger(DispatcherServlet.class);
    private UserService userService;
    private StudentService studentService;
    private TeacherService teacherService;
    private CourseService courseService;
    private CommandController commandController;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        OptionalCoursesContext optionalCoursesContext = (OptionalCoursesContext) context.getAttribute("OptionalCoursesContext");
        userService = optionalCoursesContext.getUserService();
        studentService = optionalCoursesContext.getStudentService();
        teacherService = optionalCoursesContext.getTeacherService();
        courseService = optionalCoursesContext.getCourseService();
        commandController = optionalCoursesContext.getCommandController();
        try {
            log.info(userService.getUserByLoginAndPassword("admin","admin"));
            log.info(studentService.getAllStudent());
        log.info(teacherService.getTeacherByLogin("koval.andrey@gmail.com"));
        log.info(courseService.getCourseById(34));
        log.info(courseService.getStudentFinishedCourses(2));

        } catch (SQLQueryException e) {
            e.printStackTrace();
        }
        log.info("Servlet was initialized");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("GET");
        log.info(request.getParameter("pageName") + " request parameter");
        if (request.getParameter("rolePage") != null) {
            String pageName = request.getParameter("rolePage");
            try {
                request.setAttribute("pageName", request.getParameter("pageName"));
                request.getRequestDispatcher(pageName).forward(request, response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
        } else {
            if (request.getParameter("successMessage") != null) {
                log.info(request.getParameter("successMessage"));
                request.setAttribute("successMessage", request.getParameter("successMessage"));
            } else if (request.getParameter("deniedMessage") != null) {
                request.setAttribute("deniedMessage", request.getParameter("deniedMessage"));
            }
            request.getSession().setAttribute("pageName", request.getParameter("pageName"));
            commandController.invoke(request.getParameter("pageName"), request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("POST");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        log.info(request.getParameter("pageName") + " request parameter");
        request.getSession().setAttribute("pageName", request.getParameter("pageName"));
        try {
            commandController.invoke(request.getParameter("pageName"), request, response);
        } catch (NullPointerException e) {
            commandController.invoke("loadAvatar", request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

}