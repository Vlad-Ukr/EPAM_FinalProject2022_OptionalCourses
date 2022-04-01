package com.example.optionalcoursesfp.exeption;

public class MaxAmountOfRegistrationException extends IllegalArgumentException {
    public MaxAmountOfRegistrationException(String message) {
        super(message);
    }

    public MaxAmountOfRegistrationException() {
    }
}
