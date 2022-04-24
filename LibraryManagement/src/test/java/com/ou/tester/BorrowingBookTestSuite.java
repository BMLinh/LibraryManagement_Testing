/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.tester;

import com.ou.services.BorrowingBookService;
import com.ou.pojo.BorrowingBook;
import com.ou.utils.JdbcUtils;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
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
public class BorrowingBookTestSuite {
    private static Connection conn;
    private static BorrowingBookService b;
    
     @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(UserTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        b = new BorrowingBookService();
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
        BorrowingBook bw = new BorrowingBook(1, 2, 1, 1, 1, new Date(2022,04,23), new Date(2022, 04, 30), 0, new BigDecimal(20000));
        
        Assertions.assertTrue(b.addBorrowingBook(bw));
    }
    
    @Test
    public void deleteSuccess() throws SQLException{
        Assertions.assertTrue(b.deleteBorrowingBook(2));
    }
    
    @Test
    public void updateSuccess() throws SQLException{
        Assertions.assertTrue(b.updateActive(1, 1));
    }
    
    @Test
    public void addFailed() throws SQLException{
        BorrowingBook borrowingBook = new BorrowingBook(1, -2, 1, 1, 1, new Date(122,04,23), new Date(122, 04, 30), 0, new BigDecimal(20000));
        
        Assertions.assertFalse(b.addBorrowingBook(borrowingBook));
    }
    
    @Test
    public void deleteFailed() throws SQLException{
        Assertions.assertFalse(b.deleteBorrowingBook(99));
    }
    
    @Test
    public void updateFailed() throws SQLException{
        Assertions.assertFalse(b.updateActive(1, 9));
    }
    
    @Test
    public void NotNullTest() throws SQLException{
        List<BorrowingBook> list = b.getBorrowingBooks();
        
        list.forEach(r -> Assertions.assertNotNull(r.getId()));
        list.forEach(r -> Assertions.assertNotNull(r.getStaffId()));
        list.forEach(r -> Assertions.assertNotNull(r.getBookId()));
        list.forEach(r -> Assertions.assertNotNull(r.getReaderCardId()));
        list.forEach(r -> Assertions.assertNotNull(r.getAmount()));
        list.forEach(r -> Assertions.assertNotNull(r.getCreatedDate()));
    }
}
