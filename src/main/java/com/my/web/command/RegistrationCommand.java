package com.my.web.command;

import com.my.PATH;
import com.my.db.CustomerDAO;
import com.my.db.entity.Customer;
import com.my.exception.AppException;
import com.my.exception.DBException;
import com.my.exception.Messages;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegistrationCommand extends  Command{
    private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);

    private static CustomerDAO customerDAO;

    public RegistrationCommand() {
        try {
            customerDAO = new CustomerDAO();
        } catch (DBException e) {
            LOG.error(e);
            e.printStackTrace();
        }
    }

    public RegistrationCommand(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws AppException {
        LOG.debug("Command starts");
        String forward = PATH.PAGE_REGISTRATION_USER;

        String login = request.getParameter("login");
        LOG.trace("Request parameter: login --> " + login);

        if (customerDAO.findUserByLogin(login) != null){
            LOG.trace("This login is already taken");
            request.setAttribute("error", "This login is already taken");
            return forward;
        }

        Customer customer = new Customer();

        customer.setFirst_name(request.getParameter("name"));
        customer.setLast_name(request.getParameter("lastname"));
        customer.setLogin(request.getParameter("login"));
        customer.setRole("user");
        customer.setPhone_number(request.getParameter("contact"));
        customer.setPassword_customer(request.getParameter("pass"));

        boolean b = customerDAO.addCustomer(customer);

        if (b){
            forward = PATH.PAGE_LOGIN;
            request.setAttribute("info", "Welcome, enter your login and password");
            LOG.info("User add successful");
        }else {
            LOG.error(Messages.ERR_CANNOT_ADD_CUSTOMER);
            request.setAttribute("error", "Please try again");
            return forward;
        }

        LOG.info("add customer was successful customer -> " + customer);
        return forward;
    }


}
