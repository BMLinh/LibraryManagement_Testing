package com.ou.services;

import com.ou.pojo.OrderingBook;
import com.ou.utils.JdbcUtils;
import com.ou.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderingBookService {
    public List<OrderingBook> getOrderingBooks() throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM orderingbook order by id");
            ResultSet rs = stm.executeQuery();
            
            List<OrderingBook> list = new ArrayList<>();
            while(rs.next()){
                int id = rs.getInt("id");
                int bookId = rs.getInt("book_id");
                int readerCardId = rs.getInt("reader_card_id");
                int amount = rs.getInt("amount");
                String createdDate = Utils.convertDateTimeToString(rs.getTimestamp("created_date"));
                String expiredDate = Utils.convertDateTimeToString(rs.getTimestamp("expired_date"));
                boolean active = rs.getBoolean("active");
                
                list.add(new OrderingBook(id, bookId, readerCardId, amount, createdDate, expiredDate, active));
            }
            return list;
        }
    }
    
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
            stm.execute();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    //Set envent auto cập nhật cho phiếu mượn (active , amount của sách) sau khoảng thời gian (để minute để thuận lợi cho việc test)
    public boolean setAutoUpdateOrderBook(String currentDay, int minute, int orderID) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm =conn.prepareStatement("CREATE EVENT If not exists "+currentDay+" \n" +
                    "ON SCHEDULE at current_timestamp + interval ? minute\n" +
                    "DO\n" +
                    "Update orderingbook o, book b \n" +
                    "SET o.active=true, b.amount=b.amount+o.amount\n" +
                    "WHERE o.book_id=b.id and o.id=?");
            stm.setInt(1, minute);
            stm.setInt(2, orderID);
            return stm.executeUpdate() > 0;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

    //Lấy tổng số lượng sách đặt của thẻ độc giả chưa đến nhận sách
    public int getTotalAmountByOrderID(int orderId, boolean active) throws SQLException{
        try (Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("select sum(amount) from orderingbook where reader_card_id= ? and active = ?");
            stm.setInt(1, orderId);
            stm.setBoolean(2, active);
            ResultSet rs = stm.executeQuery();
            int amount = 0;
            if (rs.next()){
                amount = rs.getInt("sum(amount)");
            }
            return amount;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            return 0;
        }
    }
    
    public boolean updateActiveOrderBook(boolean ac, int id) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("UPDATE orderingbook SET active=? WHERE id=?");
            stm.setBoolean(1, ac);
            stm.setInt(2, id);
            
            return stm.executeUpdate() > 0;
        }
    }
    
    public List<OrderingBook> findByActive(boolean active1) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM orderingbook WHERE active=?");
            stm.setBoolean(1, active1);
            ResultSet rs = stm.executeQuery();
            
            List<OrderingBook> list = new ArrayList<>();
            while(rs.next()){
               int id = rs.getInt("id");
                int bookId = rs.getInt("book_id");
                int readerCardId = rs.getInt("reader_card_id");
                int amount = rs.getInt("amount");
                String createdDate = Utils.convertDateTimeToString(rs.getTimestamp("created_date"));
                String expiredDate = Utils.convertDateTimeToString(rs.getTimestamp("expired_date"));
                boolean active = rs.getBoolean("active");
                
                list.add(new OrderingBook(id, bookId, readerCardId, amount, createdDate, expiredDate, active));
            }
            return list;
        }
    }
    
    public List<OrderingBook> findByReaderCardId(int rd) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM orderingbook WHERE reader_card_id=?");
            stm.setInt(1, rd);
            ResultSet rs = stm.executeQuery();
            
            List<OrderingBook> list = new ArrayList<>();
            while(rs.next()){
               int id = rs.getInt("id");
                int bookId = rs.getInt("book_id");
                int readerCardId = rs.getInt("reader_card_id");
                int amount = rs.getInt("amount");
                String createdDate = Utils.convertDateTimeToString(rs.getTimestamp("created_date"));
                String expiredDate = Utils.convertDateTimeToString(rs.getTimestamp("expired_date"));
                boolean active = rs.getBoolean("active");
                
                list.add(new OrderingBook(id, bookId, readerCardId, amount, createdDate, expiredDate, active));
            }
            return list;
        }
    }
}
