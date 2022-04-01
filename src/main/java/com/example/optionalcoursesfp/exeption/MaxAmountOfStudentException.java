package com.example.optionalcoursesfp.exeption;

public class MaxAmountOfStudentException extends IllegalArgumentException{
    public MaxAmountOfStudentException(String message) {
        super(message);
    }

    public MaxAmountOfStudentException() {
    }
}
