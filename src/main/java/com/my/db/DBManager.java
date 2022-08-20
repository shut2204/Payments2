package com.my.db;

import com.my.exception.DBException;
import com.my.exception.Messages;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

    private static final Logger LOG = Logger.getLogger(DBManager.class);

    private static DBManager instance;

    public static synchronized DBManager getInstance() throws DBException {
        if (instance == null){
            instance = new DBManager();
        }
        return instance;
    }

    private DataSource ds;

    private DBManager() throws DBException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env/");
            ds = (DataSource) envContext.lookup("jdbc/DBforProject");
            LOG.trace("Data source ==> " + ds);
        } catch (NamingException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE,e);
        }
    }

    public Connection getConnection() throws DBException {
        Connection con = null;

        try {
            con = ds.getConnection();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
        }

        return con;
    }

    // //////////////////////////////////////////////////////////
    // DB util methods
    // //////////////////////////////////////////////////////////

    /**
     * Closes a connection.
     *
     * @param con Connection to be closed.
     */
    private void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * close Statment
     *
     * @param stmt
     */
    private void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, e);
            }
        }
    }

    /**
     * close ResultSet
     *
     * @param rs
     */
    private void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, e);
            }
        }
    }

    /**
     * Close Connection,Statment,ResultSet
     *
     * @param con
     * @param stmt
     * @param rs
     */
    void close(Connection con, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(con);
    }

    public void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_MAKE_ROLLBACK, e);

            }
        }
    }
}
