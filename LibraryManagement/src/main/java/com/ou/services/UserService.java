/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.services;

import com.ou.pojo.User;
import com.ou.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 * @author Admin
 */
public class UserService {
    public List<User> getUsers() throws SQLException {
        try (Connection conn = JdbcUtils.getConn()){
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM user");
            
            List<User> users = new ArrayList<>();
            while(rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String fullname = rs.getString("fullname");
                byte gender = rs.getByte("gender");
                Date birth = rs.getDate("dob");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                int role_id = rs.getInt("role_id");
                int department_id = rs.getInt("department_id");
                Date created_date = rs.getDate("create_date");
            }
            
            return users;
        }
    }
}
