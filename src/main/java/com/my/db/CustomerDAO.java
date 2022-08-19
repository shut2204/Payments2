package com.my.db;

import com.my.db.entity.Customer;
import com.my.exception.DBException;
import com.my.exception.Messages;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    private static final Logger LOG = Logger.getLogger(CustomerDAO.class);
    // //////////////////////////////////////////////////////////
    // SQL queries
    // //////////////////////////////////////////////////////////
    private static final String SQL_CREATE_USER = "INSERT INTO users (id , login , pass, first_name , last_name ,mail, role) VALUES (DEFAULT,?, ?, ? , ? ,?, ?);";

    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM Customers WHERE login=? ;";

    private static final String SQL_GET_USER_LOGIN = "SELECT * FROM users ;";

    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id= ? ;";

    private static final String SQL_FIND_USER_ID_BY_ORDER = "SELECT id_user FROM request WHERE id = ? ;";

    private static final String SQL_FIND_USER_ID_BY_OWN_ORDER = "SELECT id_user FROM own_request WHERE id = ? ;";

    private final DBManager dbManager;

    public CustomerDAO() throws DBException {
        this.dbManager = DBManager.getInstance();
    }

    public CustomerDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public Customer findUserByLogin(String login) throws DBException {
        Customer customer = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("select * from Customers where login = ?");
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                customer = extractUser(rs);
            }
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_FIND_USER_BY_LOGIN, ex);
            ex.printStackTrace();
        } finally {
            DBManager.getInstance().close(con, pstmt, rs);
        }
        return customer;

    }

    private Customer extractUser(ResultSet rs) throws SQLException {
        Customer user = new Customer();
        user.setIdcustomer(rs.getInt("idcustomer"));
        user.setLogin(rs.getString("login"));
        user.setPassword_customer(rs.getString("password_customer"));
        user.setFirst_name(rs.getString("first_name"));
        user.setLast_name(rs.getString("last_name"));
        user.setRole(rs.getString("role"));
        return user;
    }

    public boolean addCustomer(Customer customer) throws DBException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int i = 0;

        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con.prepareStatement("INSERT INTO Customers(phone_number, " +
                    "first_name, last_name, login," +
                    " password_customer, role) VALUES (?,?,?,?,?,?)");
            pstmt.setString(1, customer.getPhone_number());
            pstmt.setString(2, customer.getFirst_name());
            pstmt.setString(3, customer.getLast_name());
            pstmt.setString(4, customer.getLogin());
            pstmt.setString(5, customer.getPassword_customer());
            pstmt.setString(6, customer.getRole());

            i = pstmt.executeUpdate();
        } catch (SQLException ex) {
            LOG.error(Messages.ERR_CANNOT_CREATE_USER, ex);
            throw new DBException(Messages.ERR_CANNOT_CREATE_USER, ex);
        } finally {
            DBManager.getInstance().close(con, pstmt, rs);
        }

        return i > 0;
    }
}
