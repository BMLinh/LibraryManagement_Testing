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
}
