package com.example.optionalcoursesfp.exeption;

public class BlockedStudentException extends IllegalArgumentException {

    public BlockedStudentException(String message) {
        super(message);
    }

    public BlockedStudentException() {
    }
}
