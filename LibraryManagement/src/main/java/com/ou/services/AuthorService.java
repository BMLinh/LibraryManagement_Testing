package com.ou.services;

import com.ou.pojo.Author;
import com.ou.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorService {
    public List<Author> getAuthors(String kw) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM author WHERE name like concat('%', ?, '%')");
            if (kw == null)
                kw = "";
            stm.setString(1, kw);
            ResultSet rs = stm.executeQuery();

            List<Author> authors = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                authors.add(new Author(id, name));
            }

            return authors;
        }
    }

    public Author getById(int authorId) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM author WHERE id=?");
            stm.setString(1, String.valueOf(authorId));

            ResultSet rs = stm.executeQuery();
            Author author = null;

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                author = new Author(id, name);
            }

            return author;
        }
    }

    public boolean add(Author author) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("INSERT INTO author(name) VALUES (?)");

            stm.setString(1, author.getName());

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean update(int id, String authorName) throws SQLException {
        Author currentAuthor = getById(id);
        if (currentAuthor != null) {
            try (Connection conn = JdbcUtils.getConn()) {

                PreparedStatement stm = conn.prepareStatement("UPDATE author" +
                        " SET name=?" +
                        " WHERE id=?");

                stm.setString(1, currentAuthor.getName());

                if (authorName != null) {
                    stm.setString(1, authorName);
                }
                stm.setInt(2, id);
                return stm.executeUpdate() > 0;
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean delete(int id) throws SQLException{
        try (Connection conn = JdbcUtils.getConn()){
            PreparedStatement stm = conn.prepareStatement("DELETE FROM author WHERE id=?");
            stm.setInt(1, id);

            return stm.executeUpdate() > 0;
        }
    }
}
