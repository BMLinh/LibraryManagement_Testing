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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Admin
 */
public class UserService {
    public List<User> getUser() throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user");

            ResultSet rs = stm.executeQuery();

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String fullname = rs.getString("fullname");
                int gender = rs.getByte("gender");
                Date dob = rs.getDate("dob");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                int roleId = rs.getInt("role_id");
                int departmentId = rs.getInt("department_id");

                users.add(new User(id, username, password, fullname, gender, dob, email, address, phone, roleId, departmentId));
            }

            return users;
        }
    }

    public boolean updateUser(int id, User u) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("UPDATE user SET username=?, password=?, fullname=?, gender=?, dob=?, address=?, phone=?,"
                    + "role_id=?, department_id=? WHERE id = ?");
            stm.setString(1, u.getUsername());
            stm.setString(2, u.getPassword());
            stm.setString(3, u.getFullname());
            stm.setInt(4, u.getGender());
            stm.setDate(5, Utils.convertUtilToSql(u.getDob()));
            stm.setString(6, u.getAddress());
            stm.setString(7, u.getPhone());
            stm.setInt(8, u.getRoleId());
            stm.setInt(9, u.getDepartmentId());
            stm.setInt(10, id);

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean addUser(User user) {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO "
                    + "user(username,password,fullname,gender,dob,email,address,phone,role_id,department_id) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?)");
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getPassword());
            stm.setString(3, user.getFullname());
            stm.setInt(4, user.getGender());
            stm.setDate(5, Utils.convertUtilToSql(user.getDob()));
            stm.setString(6, user.getEmail());
            stm.setString(7, user.getAddress());
            stm.setString(8, user.getPhone());
            stm.setInt(9, user.getRoleId());
            stm.setInt(10, user.getDepartmentId());

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int id) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("DELETE FROM user WHERE id=?");
            stm.setInt(1, id);

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public User findUserById(int userId) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user WHERE id = ?");

            stm.setInt(1, userId);

            ResultSet rs = stm.executeQuery();

            User user = new User();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullname(rs.getString("fullname"));
                user.setGender(rs.getByte("gender"));
                user.setDob(rs.getDate("dob"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                user.setRoleId(rs.getInt("role_id"));
                user.setDepartmentId(rs.getInt("department_id"));
            }

            return user;
        }
    }

    public List<User> findUserByPhone(String tel) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user WHERE phone like concat('%', ?, '%')");

            stm.setString(1, tel);

            ResultSet rs = stm.executeQuery();

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String fullname = rs.getString("fullname");
                int gender = rs.getByte("gender");
                Date dob = rs.getDate("dob");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                int roleId = rs.getInt("role_id");
                int departmentId = rs.getInt("department_id");

                users.add(new User(id, username, password, fullname, gender, dob, email, address, phone, roleId, departmentId));
            }

            return users;
        }
    }

    public List<User> findUserByUsername(String userUsername) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user WHERE username = ?");

            stm.setString(1, userUsername);

            ResultSet rs = stm.executeQuery();

            List<User> users = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String fullname = rs.getString("fullname");
                int gender = rs.getByte("gender");
                Date dob = rs.getDate("dob");
                String email = rs.getString(rs.getString("email"));
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                int roleId = rs.getInt("role_id");
                int departmentId = rs.getInt("department_id");

                users.add(new User(id, username, password, fullname, gender, dob, email, address, phone, roleId, departmentId));
            }

            return users;
        }
    }

    public User getByUsernamme(String username) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user WHERE username = ?");

            stm.setString(1, username);

            ResultSet rs = stm.executeQuery();

            User user = new User();
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullname(rs.getString("fullname"));
                user.setGender(rs.getByte("gender"));
                user.setDob(rs.getDate("dob"));
                user.setFullname(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                user.setRoleId(rs.getInt("role_id"));
                user.setDepartmentId(rs.getInt("department_id"));
            }

            return user;
        }
    }

}
