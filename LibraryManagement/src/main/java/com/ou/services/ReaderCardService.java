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
                int userId = rs.getInt("user_id");
                
                readerCards.add(new ReaderCard(id, startDate, endDate, amount, userId));
            }
            
            return readerCards;
        }
    }
    
    public boolean addReaderCard(ReaderCard readerCard) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("INSERT INTO "
                                                    + "readercard(start_date,end_date,amount,user_id) "
                                                    + "VALUES (?,?,?,?)");
            
            stm.setDate(1, Utils.convertUtilToSql(readerCard.getStartDate()));
            stm.setDate(2, Utils.convertUtilToSql(readerCard.getEndDate()));
            stm.setInt(3, readerCard.getAmount());
            stm.setInt(4, readerCard.getUserId());
            
            return stm.executeUpdate() > 0;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean updateReaderCard(int id, ReaderCard readerCard) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("UPDATE readercard "
                                                    + "SET start_date=?, end_date=?, amount=?, user_id=? WHERE id=?");
            
            stm.setDate(1, Utils.convertUtilToSql(readerCard.getStartDate()));
            stm.setDate(2, Utils.convertUtilToSql(readerCard.getEndDate()));
            stm.setInt(3, readerCard.getAmount());
            stm.setInt(4, readerCard.getUserId());
            stm.setInt(5, id);
            
            return stm.executeUpdate() > 0;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteReaderCard(int id) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("DELETE FROM readercard WHERE id=?");
            
            stm.setInt(1, id);
            
            return stm.executeUpdate() > 0;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return false;
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
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                int amount = rs.getInt("amount");
                int userId = rs.getInt("user_id");
                
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
                Date startDate = rs.getDate("start_date");
                Date endDate = rs.getDate("end_date");
                int amount = rs.getInt("amount");
                int userId = rs.getInt("user_id");
                
                readerCards.add(new ReaderCard(id, startDate, endDate, amount, userId));
            }
            
            return readerCards;
        }
    }
    
    public boolean updateAmount (int amount, int id) throws SQLException {
        try(Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("UPDATE readercard SET amount=? WHERE id=?");
            stm.setInt(1, amount);
            stm.setInt(2, id);
            return stm.executeUpdate() > 0;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    
}
