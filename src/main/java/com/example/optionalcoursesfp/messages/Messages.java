package com.example.optionalcoursesfp.messages;

public final class Messages {

    public static final String ERR_CANNOT_OBTAIN_CONNECTION = "Cannot obtain a connection from the pool";
    public static final String ERR_CANNOT_OBTAIN_DATA_SOURCE = "Cannot obtain the data source";
    public static final String ERR_CANNOT_EXECUTE_QUERY="Cannot execute query";
    public static final String USER_IS_ALREADY_EXISTS="User is already exists";
    public static final String COURSE_IS_ALREADY_EXISTS="Course is already exists";
    public static final String STUDENT_ALREADY_REGISTERED="Student already registered on this course";
    public static final String MAX_AMOUNT_OF_REGISTRATIONS="Student has max amount of registrations";
    public static final String BLOCKED_STUDENT="student is blocked";

    private Messages() {
        throw new UnsupportedOperationException();
    }
}
