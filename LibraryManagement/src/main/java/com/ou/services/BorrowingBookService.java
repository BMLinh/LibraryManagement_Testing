/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.services;

import com.ou.pojo.BorrowingBook;
import com.ou.utils.JdbcUtils;
import com.ou.utils.Utils;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class BorrowingBookService {
    public List<BorrowingBook> getBorrowingBooks() throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("SELECT *  FROM borrowingbook");
            ResultSet rs = stm.executeQuery();
            
            List<BorrowingBook> list = new ArrayList<>();
            
            while(rs.next()){
                int id = rs.getInt("id");
                int staffId = rs.getInt("staff_id");
                int bookId = rs.getInt("book_id");
                int readerCardId = rs.getInt("reader_card_id");
                int amount = rs.getInt("amount");
                Date createdDate = rs.getDate("created_date");
                Date returnDate = rs.getDate("return_date");
                int active = rs.getInt("active");
                BigDecimal fine = rs.getBigDecimal("fine");
                
                list.add(new BorrowingBook(id, staffId, bookId, readerCardId, amount, createdDate, returnDate, active, fine));
            }
            
            return list;
        }
    }
    
    
    public boolean addBorrowingBook(BorrowingBook borrowingBook){
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("INSERT INTO borrowingbook(staff_id,book_id,reader_card_id,amount,created_date,return_date,active,fine) "
                                                    + "VALUES(?,?,?,?,?,?,?,?)");
            
            stm.setInt(1, borrowingBook.getStaffId());
            stm.setInt(2, borrowingBook.getBookId());
            stm.setInt(3, borrowingBook.getReaderCardId());
            stm.setInt(4, borrowingBook.getAmount());
            stm.setDate(5, Utils.convertUtilToSql(borrowingBook.getCreatedDate()));
            stm.setDate(6, Utils.convertUtilToSql(borrowingBook.getReturnDate()));
            stm.setInt(7, borrowingBook.getActive());
            stm.setBigDecimal(8, borrowingBook.getFine());
            
            return stm.executeUpdate() > 0;
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteBorrowingBook(int id){
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("DELETE FROM borrowingbook WHERE id=?");
            stm.setInt(1, id);
            
            return stm.executeUpdate() > 0;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean updateActive(int id, int active){
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("UPDATE borrowingbook SET active=? WHERE id=?");
            stm.setInt(1, active);
            stm.setInt(2, id);
            
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
