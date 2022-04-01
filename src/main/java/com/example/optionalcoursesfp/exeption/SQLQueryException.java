package com.example.optionalcoursesfp.exeption;

import java.sql.SQLException;

public class SQLQueryException extends SQLException {
    public SQLQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public SQLQueryException() {
    }
}
