package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BlockStudentCommand implements Command {
    private final StudentService studentService;

    public BlockStudentCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            studentService.blockStudent(request.getParameter("studentLogin"));
            response.sendRedirect("dispatcher-servlet?pageName=showStudents");
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
}
