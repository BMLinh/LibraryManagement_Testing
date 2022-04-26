package com.ou.services;

import com.ou.pojo.Department;
import com.ou.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentService {
    public List<Department> getDepartments(String kw) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM department WHERE name like concat('%', ?, '%') order by id");
            if (kw == null)
                kw ="";
            stm.setString(1,kw);
            ResultSet rs = stm.executeQuery();
            List<Department> d = new ArrayList<>();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                d.add(new Department(id, name));
            }
            return d;
        }
    }

    public boolean deleteDepartment(int departmentId) throws SQLException{
        try (Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("DELETE FROM department WHERE id=?");
            stm.setInt(1, departmentId);

            return stm.executeUpdate() > 0;
        }
    }

    public Department getDepartmentById(int departmentId) throws SQLException{
        try (Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM department WHERE id=?");
            stm.setInt(1, departmentId);

            ResultSet rs = stm.executeQuery();
            Department d = null;
            if (rs.next()){
                d = new Department();
                d.setId(rs.getInt("id"));
                d.setName(rs.getString("name"));
            }
            return d;
        }
    }

    public boolean addDepartment(Department d) throws SQLException{
        try (Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("INSERT INTO department(name) VALUES (?)");
            stm.setString(1, d.getName());
            return stm.executeUpdate() > 0;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateDepartment(int departmentId, String departName) throws SQLException{
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("UPDATE department SET name=? WHERE id=?");
            stm.setString(1, departName);
            stm.setInt(2, departmentId);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
}
