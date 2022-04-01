package com.example.optionalcoursesfp.listener;


import com.example.optionalcoursesfp.command.commandcontroller.CommandController;
import com.example.optionalcoursesfp.util.connection.ConnectionPool;
import com.example.optionalcoursesfp.context.OptionalCoursesContext;
import com.example.optionalcoursesfp.repository.CourseRepository;
import com.example.optionalcoursesfp.repository.StudentRepository;
import com.example.optionalcoursesfp.repository.TeacherRepository;
import com.example.optionalcoursesfp.repository.UserRepository;
import com.example.optionalcoursesfp.repository.impl.CourseRepositoryImpl;
import com.example.optionalcoursesfp.repository.impl.StudentRepositoryImpl;
import com.example.optionalcoursesfp.repository.impl.TeacherRepositoryImpl;
import com.example.optionalcoursesfp.repository.impl.UserRepositoryImpl;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.StudentService;
import com.example.optionalcoursesfp.service.TeacherService;
import com.example.optionalcoursesfp.service.UserService;
import com.example.optionalcoursesfp.service.impl.CourseServiceImp;
import com.example.optionalcoursesfp.service.impl.StudentServiceImpl;
import com.example.optionalcoursesfp.service.impl.TeacherServiceImpl;
import com.example.optionalcoursesfp.service.impl.UserServiceImpl;
import com.example.optionalcoursesfp.util.transaction.TransactionManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    private static final Logger log = Logger.getLogger(ContextListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        OptionalCoursesContext context=initContext(sce);
        initLog4J(sce.getServletContext());
        sce.getServletContext().setAttribute("OptionalCoursesContext",context);
        log.info("Context was initialized");
    }

    public OptionalCoursesContext initContext(ServletContextEvent sce){
       ConnectionPool pool= new ConnectionPool();
        TransactionManager transactionManager=new TransactionManager();
        UserRepository userRepository =new UserRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository,pool,transactionManager);
        StudentRepository studentRepository=new StudentRepositoryImpl();
        StudentService studentService = new StudentServiceImpl(userRepository, studentRepository,pool,transactionManager);
        TeacherRepository teacherRepository=new TeacherRepositoryImpl();
        TeacherService teacherService= new TeacherServiceImpl(userRepository, teacherRepository,pool,transactionManager);
        CourseRepository courseRepository=new CourseRepositoryImpl();
        CourseService courseService=new CourseServiceImp(courseRepository,pool,transactionManager);
        CommandController commandController = new CommandController(userService,studentService,teacherService,courseService);
        log.info("All services,repositories and command controller was initialized ");
        return new OptionalCoursesContext(userService,studentService,teacherService,courseService,commandController);
    }
    private void initLog4J(ServletContext servletContext) {
        try {
            PropertyConfigurator.configure(servletContext.getRealPath(
                    "WEB-INF/log4j.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
