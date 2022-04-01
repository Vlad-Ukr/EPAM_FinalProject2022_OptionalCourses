package com.example.optionalcoursesfp.exeption;

import java.sql.SQLException;

public class CourseAlreadyExistException extends SQLException {
    public CourseAlreadyExistException(String message) {
        super(message);
    }

    public CourseAlreadyExistException(String courseIsAlreadyExists, SQLException e) {
    }
}
