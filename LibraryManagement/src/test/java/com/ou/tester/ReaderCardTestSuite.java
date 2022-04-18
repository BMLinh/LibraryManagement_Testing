/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.tester;

import com.ou.services.ReaderCardService;
import com.ou.pojo.ReaderCard;
import com.ou.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Admin
 */
public class ReaderCardTestSuite {
    private static Connection conn;
    private static ReaderCardService rs;
    
    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(UserTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        rs = new ReaderCardService();
    }
    
    @AfterAll
    public static void afterAll() {
        if(conn != null)
            try{
                conn.close();
            } catch (SQLException ex) {
            Logger.getLogger(UserTestSuite.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Test 
    public void addSuccess() throws SQLException{
        ReaderCard rc = new ReaderCard(99, new Date(122,04,01), new Date(130,04,01), 5, 1);
        
        Assertions.assertTrue(rs.addReaderCard(rc));
    }
    
    @Test
    public void deleteSuccess() throws SQLException{
        Assertions.assertTrue(rs.deleteReaderCard(1));
    }
    
    @Test
    public void updateSuccess() throws SQLException{
        ReaderCard rc = new ReaderCard(1, new Date(122,04,02), new Date(123,04,02), 52, 1);
        
        Assertions.assertTrue(rs.updateReaderCard(2, rc));
    }
    
    @Test
    public void addFailed() throws SQLException{
        ReaderCard rc = new ReaderCard(1, new Date(), new Date(), -12, 1);
        
        Assertions.assertFalse(rs.addReaderCard(rc));
    }
    
    @Test
    public void deleteFailed() throws SQLException{
        Assertions.assertFalse(rs.deleteReaderCard(99));
    }
    
    @Test
    public void updateFailed() throws SQLException{
        ReaderCard rc = new ReaderCard(1, new Date(), new Date(), -55, 1);
        
        Assertions.assertFalse(rs.updateReaderCard(5, rc));
    }
    
    @Test
    public void testSuccess() throws SQLException{
        Assertions.assertEquals(rs.findReaderCardsByUserId(1).get(0).getId(), 5);
    }
    
    @Test
    public void testInvalid() throws SQLException{
        Assertions.assertEquals(rs.findReaderCardsByUserId(99).size(), 0);
    }
    
    @Test
    public void testNotNull() throws SQLException{
        List<ReaderCard> list = rs.getReaderCards();
        
        list.forEach(r -> Assertions.assertNotNull(r.getId()));
        list.forEach(r -> Assertions.assertNotNull(r.getStartDate()));
        list.forEach(r -> Assertions.assertNotNull(r.getEndDate()));
        list.forEach(r -> Assertions.assertNotNull(r.getUserId()));
    }
}
