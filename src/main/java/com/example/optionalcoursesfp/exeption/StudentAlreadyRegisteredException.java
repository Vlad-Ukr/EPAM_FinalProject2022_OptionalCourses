package com.example.optionalcoursesfp.exeption;


public class StudentAlreadyRegisteredException extends IllegalArgumentException {

    public StudentAlreadyRegisteredException(String message) {
        super(message);
    }

    public StudentAlreadyRegisteredException() {
    }
}

