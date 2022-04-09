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
import java.util.Map;
/**
 *
 * @author Admin
 */
public class UserService {
    public List<User> getUsers() throws SQLException {
        try(Connection conn = JdbcUtils.getConn()){
            ResultSet rs = conn.createStatement().executeQuery("SELECT * from user");
            
            List<User> users = new ArrayList<>();
            
            while(rs.next()){
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String fullname = rs.getString("fullname");
                byte gender = rs.getByte("gender");
                Date birth = rs.getDate("dob");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                int roleId = rs.getInt("role_id");
                int departmentId = rs.getInt("department_id");
                Date createdDate = rs.getDate("created_date");
                
                users.add(new User(id, username, password, fullname, gender, birth, address, phone, roleId, departmentId, createdDate));
            }
                
            return users;
        }
    }
    
    public boolean addUser(User user) throws SQLException {
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
            stm.setInt(9, user.getRoleId());
            stm.setInt(10, user.getDepartmentId());
            stm.setDate(11, Utils.convertUtilToSql(user.getCreatedDate()));
            
            return stm.executeUpdate() > 0;
        }
    }
    
    public boolean deleteUser(int id) throws SQLException {
        try(Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("DELETE FROM user WHERE id=?");
            stm.setInt(1, id);
            
            return stm.executeUpdate() > 0;
        }
    }
    
    public List<User> findUser(Map<String, String> params) throws SQLException {
        try(Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user ?");
            if(params != null) {
                String temp = "WHERE ";
                
                if(params.containsKey("id"))
                    temp += "id = " + params.get("id") + "AND ";
                if(params.containsKey("username"))
                    temp += "username = '" + params.get("username") + "' AND ";
                if(params.containsKey("fullname"))
                    temp += "fullname like N'" + params.get("fullname") + "' AND ";
                if(params.containsKey("gender"))
                    temp += "gender = " + params.get("gender") + " AND ";
                if(params.containsKey("from_date"))
                    temp += "dob >= '" + params.get("from_date") + ", AND ";
                else if(params.containsKey("to_date"))
                        temp += "dob <= '" + params.get("to_date") + "' AND ";
                else if (params.containsKey("from_date") && params.containsKey("to_date"))
                        temp += "dob BETWEEN '" + params.get("from_date") + "' AND '" + params.get("to_date") + "' AND ";
                if(params.containsKey("address"))
                    temp += "address like N'" + params.get("id") + "' AND ";
                if(params.containsKey("phone"))
                    temp += "phone = '" + params.get("id") + "' AND ";
                if(params.containsKey("roleId"))
                    temp += "role_id = " + params.get("roleId") + " AND ";
                if(params.containsKey("departmentId"))
                    temp += "department_id = " + params.get("departmentId") + " AND ";
                if(params.containsKey("createdDate"))
                    temp += "created_date = " + params.get("id") + " AND ";
                
                stm.setString(1, temp);
            }
            
            ResultSet rs = stm.executeQuery();
            
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
                int roleId = rs.getInt("role_id");
                int departmentId = rs.getInt("department_id");
                Date createdDate = rs.getDate("created_date");
                
                users.add(new User(id, username, password, fullname, gender, birth, address, phone, roleId, departmentId, createdDate));
            }
            
            return users;
        }
    }
    
    public boolean updateUser(int id, Map<String, String> params) throws SQLException{
        try(Connection conn = JdbcUtils.getConn()){
            String temp = "";
            PreparedStatement stm = conn.prepareStatement("UPDATE user SET ? WHERE id = ?");
            stm.setInt(2, id);
            
            if(!params.isEmpty()){
                if(params.containsKey("username"))
                    temp += "username = '" + params.get("username") + "' , ";
                if(params.containsKey("fullname"))
                    temp += "fullname = N'" + params.get("fullname") + "' , ";
                if(params.containsKey("gender"))
                    temp += "gender = " + params.get("gender") + " , ";
                if(params.containsKey("birth"))
                    temp += "dob = '" + params.get("birth") + "' , ";
                if(params.containsKey("address"))
                    temp += "address = N'" + params.get("id") + "' , ";
                if(params.containsKey("phone"))
                    temp += "phone = '" + params.get("id") + "' , ";
                if(params.containsKey("roleId"))
                    temp += "role_id = " + params.get("roleId") + " , ";
                if(params.containsKey("departmentId"))
                    temp += "department_id = " + params.get("departmentId") + " , ";
            }
            stm.setString(1, temp);
            
            return stm.executeUpdate() > 0;
        }
    }
}
