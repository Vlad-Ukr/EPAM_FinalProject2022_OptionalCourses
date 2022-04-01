package com.example.optionalcoursesfp.exeption;

public class TransactionException extends DatabaseException {

    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException() {
        super();
    }
}
