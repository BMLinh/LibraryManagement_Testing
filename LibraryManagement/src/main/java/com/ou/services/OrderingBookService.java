package com.ou.services;

import com.ou.pojo.OrderingBook;
import com.ou.utils.JdbcUtils;
import com.ou.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderingBookService {
    public boolean addOrderBook(OrderingBook order) throws SQLException {
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("INSERT INTO " +
                    "orderingbook(book_id, reader_card_id, amount, created_date, expired_date) " +
                    "VALUES (?,?,?,?,?)");
            stm.setInt(1, order.getBookId());
            stm.setInt(2,order.getReaderCardId());
            stm.setInt(3, order.getAmount());
            stm.setString(4, order.getCreatedDate());
            stm.setString(5, order.getExpiredDate());
            return stm.executeUpdate() > 0;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
