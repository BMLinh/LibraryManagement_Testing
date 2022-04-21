/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou.services;

import com.ou.pojo.BookCategory;
import com.ou.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Linh
 */
public class BookCategoryService {

    public List<BookCategory> getBookCategories () throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("*SELECT * FROM bookcagory");

            List<BookCategory> bookcates = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String position = rs.getString("position");
                bookcates.add(new BookCategory(id, name, position));
            }
        
            return bookcates;
        }    
    }
}
