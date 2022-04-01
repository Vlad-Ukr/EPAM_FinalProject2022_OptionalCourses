package com.example.optionalcoursesfp.exeption;

public class UserAlreadyExistException extends IllegalArgumentException{
    public UserAlreadyExistException(String message) {
        super(message);
    }

    public UserAlreadyExistException() {
    }
}
