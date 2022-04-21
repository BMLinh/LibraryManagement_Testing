/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou.services;

import com.ou.pojo.PublishingCompany;
import com.ou.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Linh
 */
public class PublishingCompanyService {
    public List<PublishingCompany> getPublishingCompanys(String kw) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM publishingcompany WHERE name like concat('%', ?, '%')");
            if (kw == null)
                kw ="";
            stm.setString(1,kw);
            ResultSet rs = stm.executeQuery();
            List<PublishingCompany> p = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                p.add(new PublishingCompany(id, name));
            }
            return p;
        }
    }
    
    public boolean deletePublishingCompany(String publishingCompanyId) throws SQLException{
        try (Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("DELETE FROM publishingcompany WHERE id=?");
            stm.setString(1, publishingCompanyId);
            return stm.executeUpdate() > 0;
        }
    }

    public PublishingCompany getPublishingCompanyById(String publishingCompanyId) throws SQLException{
        try (Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM publishingcompany WHERE id=?");
            stm.setString(1, publishingCompanyId);
            ResultSet rs = stm.executeQuery();
            PublishingCompany p = null;
            if (rs.next()){
                p = new PublishingCompany();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
            }
            return p;
        }
    }

    public boolean addPublishingCompany(PublishingCompany p) throws SQLException{
        try (Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("INSERT INTO publishingcompany(name) VALUES (?)");
            stm.setString(1, p.getName());
            return stm.executeUpdate() > 0;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateDepartment(String publishingcompanyId, String publishingcompanyName) throws SQLException{
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("UPDATE publishingcompany SET name=? WHERE id=?");
            stm.setString(1, publishingcompanyName);
            stm.setString(2, publishingcompanyId);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
}
