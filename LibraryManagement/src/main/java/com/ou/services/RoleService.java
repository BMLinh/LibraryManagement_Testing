package com.ou.services;

import com.ou.pojo.Role;
import com.ou.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleService {
    public List<Role> getRoles(String kw) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM role WHERE name like concat('%', ?, '%')");
            if (kw == null)
                kw = "";
            stm.setString(1, kw);
            ResultSet rs = stm.executeQuery();
            List<Role> roles = new ArrayList<>();

            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                roles.add(new Role(id, name));
            }
            return roles;
        }
    }

    public boolean deleteRole(String roleId) throws  SQLException{
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("DELETE FROM role WHERE id=?");
            stm.setString(1, roleId);

            return stm.executeUpdate() > 0;
        }
    }

    //Để đây chưa biết dùng cho việt gì không
    public  Role getRoleById(String roleId) throws  SQLException{
        try (Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM role WHERE id=?");
            stm.setString(1, roleId);

            ResultSet rs = stm.executeQuery();
            Role r = null;
            if (rs.next()){
                r = new Role();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
            }
            return r;
        }
    }

    public boolean addRole(Role r) throws SQLException{
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO role(name) VALUES (?)");
            stm.setString(1, r.getName());
            return stm.executeUpdate() > 0;
        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateRole(String roleId, String roleName) throws SQLException{
        try (Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("UPDATE role SET name=? WHERE id=?");
            stm.setString(1, roleName);
            stm.setString(2, roleId);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
}
