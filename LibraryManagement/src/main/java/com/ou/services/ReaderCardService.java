/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.services;

import com.ou.pojo.ReaderCard;
import com.ou.utils.JdbcUtils;
import com.ou.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author Admin
 */
public class ReaderCardService {
    public List<ReaderCard> getReaderCards() throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM readercard");
            
            ResultSet rs = stm.executeQuery();
            
            List<ReaderCard> readerCards = new ArrayList<>();
            while(rs.next()){
                int id = rs.getInt("id");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                int amount = rs.getInt("amount");
                int userId = rs.getInt("userId");
                
                readerCards.add(new ReaderCard(id, startDate, endDate, amount, userId));
            }
            
            return readerCards;
        }
    }
    
    public boolean addReaderCard(ReaderCard readerCard) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("INSERT INTO "
                                                    + "readercard(start_date,end_date,amount,userId) "
                                                    + "VALUES (?,?,?,?)");
            
            stm.setDate(1, Utils.convertUtilToSql(readerCard.getStartDate()));
            stm.setDate(2, Utils.convertUtilToSql(readerCard.getEndDate()));
            stm.setInt(3, readerCard.getAmount());
            stm.setInt(4, readerCard.getUserId());
            
            return stm.executeUpdate() > 0;
        }
    }
    
    public boolean updateReaderCard(int id, ReaderCard readerCard) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("UPDATE readercard "
                                                    + "SET start_date=?, end_date=?, amount=?, userId=? WHERE id=?");
            
            stm.setDate(1, Utils.convertUtilToSql(readerCard.getStartDate()));
            stm.setDate(2, Utils.convertUtilToSql(readerCard.getEndDate()));
            stm.setInt(3, readerCard.getAmount());
            stm.setInt(4, readerCard.getUserId());
            stm.setInt(5, id);
            
            return stm.executeUpdate() > 0;
        }
    }
    
    public boolean deleteReaderCard(int id) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("DELETE FROM readercard WHERE id=?");
            
            stm.setInt(1, id);
            
            return stm.executeUpdate() > 0;
        }
    }
    
    public List<ReaderCard> findReaderCardById(int readerCardId) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM readercard WHERE id=?");
            stm.setInt(1, readerCardId);
            
            ResultSet rs = stm.executeQuery();
            
            List<ReaderCard> readerCards = new ArrayList<>();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                int amount = rs.getInt("amount");
                int userId = rs.getInt("userId");
                
                readerCards.add(new ReaderCard(id, startDate, endDate, amount, userId));
            }
            
            return readerCards;
        }
    }
    
    public List<ReaderCard> finReaderCardsByName(String readerCardName) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM readercard WHERE name like concat('%', ? , '%')");
            stm.setString(1, readerCardName);
            
            ResultSet rs = stm.executeQuery();
            
            List<ReaderCard> readerCards = new ArrayList<>();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                int amount = rs.getInt("amount");
                int userId = rs.getInt("userId");
                
                readerCards.add(new ReaderCard(id, startDate, endDate, amount, userId));
            }
            
            return readerCards;
        }
    }
    
    public List<ReaderCard> findReaderCardsByDate(Date fromDate, Date toDate, String date) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM readercard WHERE ? between '?' and '?'");
            stm.setString(1, date);
            stm.setDate(2, Utils.convertUtilToSql(fromDate));
            stm.setDate(3, Utils.convertUtilToSql(toDate));
            
            ResultSet rs = stm.executeQuery();
            
            List<ReaderCard> readerCards = new ArrayList<>();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                int amount = rs.getInt("amount");
                int userId = rs.getInt("userId");
                
                readerCards.add(new ReaderCard(id, startDate, endDate, amount, userId));
            }
            
            return readerCards;
        }
    }
    
    public List<ReaderCard> findReaderCardsByAmount(int readerCardAmount) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM readercard WHERE amount=?");
            stm.setInt(1, readerCardAmount);

            
            ResultSet rs = stm.executeQuery();
            
            List<ReaderCard> readerCards = new ArrayList<>();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                int amount = rs.getInt("amount");
                int userId = rs.getInt("userId");
                
                readerCards.add(new ReaderCard(id, startDate, endDate, amount, userId));
            }
            
            return readerCards;
        }
    }
    
    public List<ReaderCard> findReaderCardsByUserId(int readerCardUserId) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM readercard WHERE user_id=?");
            stm.setInt(1, readerCardUserId);

            
            ResultSet rs = stm.executeQuery();
            
            List<ReaderCard> readerCards = new ArrayList<>();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                int amount = rs.getInt("amount");
                int userId = rs.getInt("userId");
                
                readerCards.add(new ReaderCard(id, startDate, endDate, amount, userId));
            }
            
            return readerCards;
        }
    }
    
}
