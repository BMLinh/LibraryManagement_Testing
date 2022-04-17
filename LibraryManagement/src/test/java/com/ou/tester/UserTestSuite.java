/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.tester;

import com.ou.services.UserService;
import com.ou.pojo.User;
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
public class UserTestSuite {
    private static Connection conn;
    private static UserService u;
    
    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(UserTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        u = new UserService();
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
        User user = new User(1 ,"A", "123", "AAA", 0, new Date(2002, 10, 12), "Nguyễn Hiền", "0988888888", 1, 1, new Date(2022, 5, 12));
        
        Assertions.assertTrue(u.addUser(user));
    }
    
    @Test
    public void updateSuccess() throws SQLException{
        List<User> users = u.getUser();
        User user = new User(1 ,"A", "123", "AAA", 0, new Date(2002, 10, 12), "Nguyễn Hiền", "0988888888", 1, 1, new Date(2022, 5, 12));
        
        Assertions.assertTrue(u.updateUser(users.get(0).getId(),user));
    }
    
    @Test
    public void deleteSuccess() throws SQLException {
        Assertions.assertTrue(u.deleteUser(20));
    }
    
    @Test
    public void addFailed(){

    }
}
