/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou.services;

import com.ou.pojo.BookCategory;
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
public class BookCategoryService {
    public List<BookCategory> getBookCategory(String kw) throws SQLException {
            try (Connection conn = JdbcUtils.getConn()) {
                PreparedStatement stm = conn.prepareStatement("SELECT * FROM bookcategory WHERE name like concat('%', ?, '%')");
                if (kw == null)
                    kw ="";
                stm.setString(1,kw);
                ResultSet rs = stm.executeQuery();
                List<BookCategory> b = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String position = rs.getString("position");
                    b.add(new BookCategory(id, name, position));
                }
                return b;
            }
        }

        public boolean deleteBookCategory(String bookCategoryId) throws SQLException{
            try (Connection conn = JdbcUtils.getConn()){
                PreparedStatement stm = conn.prepareStatement("DELETE FROM bookcategory WHERE id=?");
                stm.setString(1, bookCategoryId);
                return stm.executeUpdate() > 0;
            }
        }

        public BookCategory getBookCategoryById(String bookCategoryId) throws SQLException{
            try (Connection conn = JdbcUtils.getConn()){
                PreparedStatement stm = conn.prepareStatement("SELECT * FROM bookcategory WHERE id=?");
                stm.setString(1, bookCategoryId);
                ResultSet rs = stm.executeQuery();
                BookCategory b = null;
                if (rs.next()){
                    b = new BookCategory();
                    b.setId(rs.getInt("id"));
                    b.setName(rs.getString("name"));
                    b.setPosition("position");
                }
                return b;
            }
        }

        public boolean addBookCategory(BookCategory b) throws SQLException{
            try (Connection conn = JdbcUtils.getConn()){
                PreparedStatement stm = conn.prepareStatement("INSERT INTO bookcategory(name, position) VALUES (?, ?)");
                stm.setString(1, b.getName());
                stm.setString(2, b.getPosition());
                return stm.executeUpdate() > 0;
            }
            catch (SQLException ex){
                ex.printStackTrace();
                return false;
            }
        }

        public boolean updateBookCategory(String BookCategoryId, String BookCategoryName, String BookPosition) throws SQLException{
            try (Connection conn = JdbcUtils.getConn()) {
                PreparedStatement stm = conn.prepareStatement("UPDATE bookcategory SET name=?, position=? WHERE id=?");
                stm.setString(1, BookCategoryName);
                stm.setString(2, BookPosition);
                stm.setString(3, BookCategoryId);
                return stm.executeUpdate() > 0;
            } catch (SQLException ex){
                ex.printStackTrace();
                return false;
            }
        }
}
