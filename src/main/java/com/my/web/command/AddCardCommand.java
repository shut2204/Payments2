package com.my.web.command;

import com.my.PATH;
import com.my.db.CardDAO;
import com.my.db.CustomerDAO;
import com.my.db.entity.Card;
import com.my.db.entity.Customer;
import com.my.exception.AppException;
import com.my.exception.DBException;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AddCardCommand extends Command{

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);

    private static CustomerDAO customerDAO;
    private static CardDAO cardDAO;


    public AddCardCommand() {
        try {
            customerDAO = new CustomerDAO();
            cardDAO = new CardDAO();
        } catch (DBException e) {
            LOG.error(e);
            e.printStackTrace();
        }
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws AppException {
        LOG.debug("Command starts");
        HttpSession session = request.getSession();

        String forward = PATH.PAGE_CABINET_USER;

        String login = (String) session.getAttribute("login");
        LOG.trace("Request parameter: login --> " + login);

        int id = customerDAO.findUserIDByLogin(login);
        LOG.trace("Found in DB: id user --> " + id);

        if (id == -1){
            LOG.error("Not found userId with by this login");
            request.setAttribute("error", "Something went wrong, please try again");
            return forward;
        }

        Card card = new Card();
        card.setName_card(Integer.parseInt(request.getParameter("type")) == 1 ? "Personal" : "Special");
        card.setBalance(0);
        card.setIdcustomer(id);

        if (!cardDAO.add(card)) {
            request.setAttribute("error", "Something went wrong, please try again");
        }

        session.setAttribute("cards",cardDAO.getAllByLogin(login));

        return forward;
    }
}
