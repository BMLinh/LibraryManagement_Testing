/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ou.services;

import com.ou.pojo.User;
import com.ou.utils.JdbcUtils;
import com.ou.utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.scene.control.Alert;
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
    
    public void addUser(User user) throws SQLException {
        try(Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO "
                                            + "user(id,username,password,fullname,gender,dob,address,phone,role_id,department_id,create_date) "
                                            + "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            stm.setInt(1, user.getId());
            stm.setString(2, user.getUsername());
            stm.setString(3, user.getPassword());
            stm.setString(4, user.getFullname());
            stm.setByte(5, user.getGender());
            stm.setDate(6, Utils.convertUtilToSql(user.getBirth()));
            stm.setString(7, user.getAddress());
            stm.setString(8, user.getPhone());
            stm.setInt(9, user.getRole_id());
            stm.setInt(10, user.getDepartment_id());
            stm.setDate(11, Utils.convertUtilToSql(user.getCreated_date()));
        }
    }
}
