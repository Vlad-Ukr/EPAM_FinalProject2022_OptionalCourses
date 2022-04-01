package com.example.optionalcoursesfp.util.connection;



import com.example.optionalcoursesfp.exeption.DatabaseException;
import com.example.optionalcoursesfp.messages.Messages;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionPool {
    private DataSource ds;
    public ConnectionPool() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/optionaldb");
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
    }

    public Connection getConnection() throws DatabaseException {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new DatabaseException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
        }
    }
}