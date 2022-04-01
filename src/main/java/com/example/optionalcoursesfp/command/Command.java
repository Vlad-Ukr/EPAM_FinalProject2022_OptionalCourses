package com.example.optionalcoursesfp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    void executeCommand(HttpServletRequest request, HttpServletResponse response);


}
