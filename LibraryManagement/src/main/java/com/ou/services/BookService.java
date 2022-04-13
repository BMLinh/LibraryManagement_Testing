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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * @author Lightning
 */
public class BookService {

    public List<Book> getBooks(String kw) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM book WHERE name like concat('%', ?, '%')");
            if (kw == null)
                kw = "";
            stm.setString(1, kw);
            ResultSet rs = stm.executeQuery();

            List<Book> books = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String descriptions = rs.getString("descriptions");
                int amount = rs.getInt("amount");
                Date publishingYear = rs.getDate("publishing_year");
                int publishingCompanyId = rs.getInt("publishing_company_id");
                int categoryId = rs.getInt("category_id");
                Date dateOfEntering = rs.getDate("date_of_entering");

                books.add(new Book(id, name, descriptions, amount, publishingYear,
                        publishingCompanyId, categoryId, dateOfEntering));
            }

            return books;
        }
    }

    public Book getBookById(int bookId) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM book WHERE id=?");
            stm.setString(1, String.valueOf(bookId));

            ResultSet rs = stm.executeQuery();
            Book book = null;

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String descriptions = rs.getString("descriptions");
                int amount = rs.getInt("amount");
                Date publishingYear = rs.getDate("publishing_year");
                int publishingCompanyId = rs.getInt("publishing_company_id");
                int categoryId = rs.getInt("category_id");
                Date dateOfEntering = rs.getDate("date_of_entering");

                book = new Book(id, name, descriptions, amount, publishingYear,
                        publishingCompanyId, categoryId, dateOfEntering);
            }

            return book;
        }
    }

    public boolean addBook(Book book) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO book(name, descriptions, amount, publishing_year, " +
                    "category_id, publishing_company_id, date_of_entering) VALUES (?, ?, ?, ?, ?, ?, ?)");

            stm.setString(1, book.getName());
            stm.setString(2, book.getDescriptions());
            stm.setInt(3, book.getAmount());
            stm.setDate(4, (java.sql.Date) book.getPublishingYear());
            stm.setInt(5, book.getCategoryId());
            stm.setInt(6, book.getPublishingCompanyId());
            stm.setDate(7, (java.sql.Date) book.getDateOfEntering());

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateBook(int bookId, Map<String, String> param) throws SQLException {
        Book currrentBook = getBookById(bookId);
        if (currrentBook != null) {
            try (Connection conn = JdbcUtils.getConn()) {

                PreparedStatement stm = conn.prepareStatement("UPDATE book" +
                        " SET name=?, descriptions=?, amount=?, publishing_year=?, category_id=?, publishing_company_id=?, date_of_entering=?" +
                        " WHERE id=?");

                stm.setString(1, currrentBook.getName());
                stm.setString(2, currrentBook.getDescriptions());
                stm.setInt(3, currrentBook.getAmount());
                stm.setDate(4, (java.sql.Date) currrentBook.getPublishingYear());
                stm.setInt(5, currrentBook.getCategoryId());
                stm.setInt(6, currrentBook.getPublishingCompanyId());
                stm.setDate(7, (java.sql.Date) currrentBook.getDateOfEntering());

                if (param != null) {
                    if (param.containsKey("name")){
                        stm.setString(1, param.get("name"));
                    }
                    if (param.containsKey("descriptions")){
                        stm.setString(2, param.get("descriptions"));
                    }
                    if (param.containsKey("amount")){
                        stm.setInt(3, Integer.parseInt(param.get("amount")));
                    }
                    if (param.containsKey("publishingYear")){
                        stm.setDate(4, java.sql.Date.valueOf(param.get("publishing_year")));
                    }
                    if (param.containsKey("categoryId")){
                        stm.setInt(5, Integer.parseInt(param.get("category_id")));
                    }
                    if (param.containsKey("publishingCompanyId")){
                        stm.setInt(6, Integer.parseInt(param.get("publishing_company_id")));
                    }
                    if (param.containsKey("dateOfEntering")){
                        stm.setDate(7, java.sql.Date.valueOf(param.get("date_of_entering")));
                    }
                }
                stm.setInt(8, bookId);
                return stm.executeUpdate() > 0;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean deleteBook(int bookId) throws SQLException{
        try (Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("DELETE FROM book WHERE id=?");
            stm.setInt(1, bookId);

            return stm.executeUpdate() > 0;
        }
    }
}