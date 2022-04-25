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
import java.util.Date;
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
        
        User user1 = u.getByUsername("A");
        Assertions.assertEquals(user1.getFullname(), "AAA");
    }
    
    @Test
    public void updateSuccess() throws SQLException{
        List<User> users = u.getUser();
        User user = new User(1 ,"A", "1234", "AAA", 0, new Date(2002, 10, 12), "Nguyễn Hiền", "0988888888", 1, 1, new Date(2022, 5, 12));
        User user1 = u.getByUsername("A");
        
        Assertions.assertTrue(u.updateUser(user1.getId(),user));
        
        User user2 = u.getByUsername("A");
        Assertions.assertEquals(user2.getPassword(), "1234");
    }
    
    @Test
    public void deleteSuccess() throws SQLException {
        User user = u.getByUsername("A");
        Assertions.assertTrue(u.deleteUser(user.getId()));
        
        User user1 = u.getByUsername("A");
        Assertions.assertNull(user1.getFullname());
    }
    
    @Test
    public void addFailed() throws SQLException{
        User user = new User(1 ,"A", "123", "AAA", 0, new Date(2002, 10, 12), "Nguyễn Hiền", "093333333333", 1, 1, new Date(2022, 5, 12));

        Assertions.assertFalse(u.addUser(user));
    }
    
    @Test
    public void updateFailed() throws SQLException {
        List<User> users = u.getUser();
        User user = users.get(0);
        user.setPassword("asdfasdf");
        
        Assertions.assertFalse(u.updateUser(0, user));
    }
    
    @Test
    public void deleteFailed() throws SQLException {
        Assertions.assertFalse(u.deleteUser(999));
    }
    
    @Test
    public void searchSuccessfull() throws SQLException{
        String kw = "09";
        List<User> users = u.findUserByPhone(kw);
        
        for(User u : users)
            Assertions.assertTrue(u.getPhone().contains(kw));
    }
    
    @Test
    public void searchInvalid() throws SQLException{
        String kw = "000000";
        List<User> users = u.findUserByPhone(kw);
        
        Assertions.assertEquals(users.size(), 0);
    }
    
    @Test
    public void searchUnscure() throws SQLException{
        String kw = "1 OR 1=1";
        List<User> users = u.findUserByPhone(kw);
        
        Assertions.assertEquals(users.size(), 0);
    }
    
    @Test
    public void usernameUniqueTest() throws SQLException{
        List<User> users = u.getUser();
        
        List<String> list = users.stream().flatMap(r -> Stream.of(r.getUsername())).collect(Collectors.toList());
        Set<String> set = new HashSet<>(list);
        
        Assertions.assertEquals(list.size(), set.size());
    }
    
    @Test
    public void phoneUniqueTest() throws SQLException {
        List<User> users = u.getUser();
        
        List<String> list = users.stream().flatMap(r -> Stream.of(r.getPhone())).collect(Collectors.toList());
        Set<String> set = new HashSet<>(list);
        
        Assertions.assertEquals(list.size(), set.size());
    }
    
    @Test
    public void testNotNull() throws SQLException{
        List<User> users = u.getUser();
        
        users.forEach(r -> Assertions.assertNotNull(r.getId()));
        users.forEach(r -> Assertions.assertNotNull(r.getUsername()));
        users.forEach(r -> Assertions.assertNotNull(r.getPassword()));
        users.forEach(r -> Assertions.assertNotNull(r.getFullname()));
        users.forEach(r -> Assertions.assertNotNull(r.getGender()));
        users.forEach(r -> Assertions.assertNotNull(r.getPhone()));
        users.forEach(r -> Assertions.assertNotNull(r.getRoleId()));
        users.forEach(r -> Assertions.assertNotNull(r.getDepartmentId()));
        users.forEach(r -> Assertions.assertNotNull(r.getCreatedDate()));        
    }

}
