/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou.services;

import java.util.List;
import com.ou.pojo.Book;
import com.ou.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Lightning
 */

public class BookService {

    public List<Book> getBooks() throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM book");

            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int amount = rs.getInt("amount");
                int publishingYear = rs.getInt("publishing_year");
                int publishingCompanyId = rs.getInt("publishing_company_id");
                int categoryId = rs.getInt("category_id");
                Date dateOfEntering = rs.getDate("date_of_entering");
                String position = rs.getString("position");
            }

            return books;
        }
    }
}
