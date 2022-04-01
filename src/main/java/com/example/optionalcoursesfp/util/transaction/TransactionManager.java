package com.example.optionalcoursesfp.util.transaction;

import com.example.optionalcoursesfp.exeption.CourseAlreadyExistException;
import com.example.optionalcoursesfp.exeption.DatabaseException;
import com.example.optionalcoursesfp.exeption.SQLQueryException;
import com.example.optionalcoursesfp.exeption.TransactionException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private static final Logger log = Logger.getLogger(TransactionManager.class);

    public void doTransaction(Connection connection,
                              TransactionConsumer<Connection> connectionConsumer)
            throws SQLException {
        try {
            connection.setAutoCommit(false);
            log.info("Set AutoCommit to false");
            log.info("Executing consumer");
            connectionConsumer.accept(connection);
            log.info("Consumer successfully executed");
            connection.commit();
            log.info("Changes committed");
        } catch (SQLQueryException e) {
            log.error("Oops! An error occurred, doing a rollback");
            connection.rollback();
            log.error("Successful rollback");
            throw new TransactionException(e.getMessage(), e);
        }
    }

    @FunctionalInterface
    public interface TransactionConsumer<T> {
        void accept(T t) throws DatabaseException, SQLQueryException, CourseAlreadyExistException;
    }

}
