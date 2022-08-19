package com.my.db;

import com.my.db.entity.Card;
import com.my.exception.DBException;
import com.my.exception.Messages;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardDAO {

    private static final Logger LOG = Logger.getLogger(CardDAO.class);

    private final DBManager dbManager;

    public CardDAO() throws DBException {
        this.dbManager = DBManager.getInstance();
    }

    public CardDAO(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public List<Card> getAllByLogin(String login) throws DBException {
        ArrayList<Card> cards = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            con = DBManager.getInstance().getConnection();
            pstmt = con
                    .prepareStatement("SELECT * FROM Cards INNER JOIN Customers ON Cards.idcustomer = Customers.idcustomer WHERE login = ?;");

            pstmt.setString(1, login);
            rs = pstmt.executeQuery();

            while (rs.next()){
                Card card = new Card();

                card.setIdcard(rs.getInt("idcard"));
                card.setIdcustomer(rs.getInt("idcustomer"));
                card.setBalance(rs.getInt("balance"));
                card.setName_card(rs.getString("name_card"));

                cards.add(card);
            }
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_GET_ALL_CARDS, e);
            e.printStackTrace();
        }

        return cards;
    }
}
