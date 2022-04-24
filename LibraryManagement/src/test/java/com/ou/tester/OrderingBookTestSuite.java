/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.tester;

import com.ou.pojo.OrderingBook;
import com.ou.services.OrderingBookService;
import com.ou.utils.JdbcUtils;
import com.ou.utils.Utils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Date;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
/**
 *
 * @author Admin
 */
public class OrderingBookTestSuite {
    private static Connection conn;
    private static OrderingBookService o;
    
    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(UserTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        o = new OrderingBookService();
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
        OrderingBook order = new OrderingBook(0, 1, 1, 1, Utils.convertDateTimeToString(new Timestamp(new Date().getTime())), Utils.convertDateTimeToString(new Timestamp(new Date(2023,04,12).getTime())), true);
        
        Assertions.assertTrue(o.addOrderBook(order));
    }
    
    @Test
    public void updateActiveSuccess() throws SQLException{
        Assertions.assertTrue(o.updateActiveOrderBook(true, 1));
    }
    
    @Test
    public void findSuccess() throws SQLException{
        boolean flag = false;
        
        Assertions.assertTrue(o.findByActive(flag).size() > 0);
    }
    
    @Test 
    public void findInvalid() throws SQLException{
        Assertions.assertFalse(o.findByActive(true).size() > 0);
    }
    
    @Test
    public void addFailed() throws SQLException{
        OrderingBook order = new OrderingBook(0, -1, -1, 1, Utils.convertDateTimeToString(new Timestamp(new Date().getTime())), Utils.convertDateTimeToString(new Timestamp(new Date(2023,04,12).getTime())), true);
        
        Assertions.assertFalse(o.addOrderBook(order));
    }
    
    @Test
    public void updateFailed() throws SQLException{
        Assertions.assertFalse(o.updateActiveOrderBook(true, -1));
    }
}
