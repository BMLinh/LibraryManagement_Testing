package com.ou.tester;

import com.ou.pojo.Author;
import com.ou.services.AuthorService;
import com.ou.utils.JdbcUtils;
import jdk.jfr.Name;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorTestSuite {
    private static Connection conn;
    private static AuthorService authorService;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(AuthorTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }

        authorService = new AuthorService();
    }

    @AfterAll
    public static void afterAll() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(AuthorTestSuite.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Test
    public void searchSuccessful() throws SQLException {
        String kw = "search";
        List<Author> authors = authorService.getAuthors(kw);

        Assertions.assertTrue(authors.size() > 0);
    }

    @Test
    public void searchInvalid() throws SQLException {
        String kw = "searchs";
        List<Author> authors = authorService.getAuthors(kw);

        Assertions.assertEquals(authors.size(), 0);
    }

    @Test
    @Name("SQL Injection")
    public void testSearchUnscure() throws SQLException {
        String kw = "1 OR 1=1";
        List<Author> authors = authorService.getAuthors(kw);

        Assertions.assertEquals(authors.size(), 0);
    }

    @Test
    public void deleteSuccessful() throws SQLException {
        int id = 2;
        Assertions.assertTrue(authorService.delete(id));
    }

    @Test
    public void deleteFailed() throws SQLException {
        int id = 11111;
        Assertions.assertFalse(authorService.delete(id));
    }

    @Test
    @Name("Them tac gia thanh cong")
    public void addSuccessful() throws SQLException {
        String name = "addSuccessful test";
        Author author = new Author();
        author.setName(name);
        Assertions.assertTrue(authorService.add(author));

        Author authorAdded = authorService.getAuthors(name).get(0);
        Assertions.assertEquals(authorAdded.getName().
                compareToIgnoreCase(author.getName()), 0);
    }

    @Test
    public void updateSuccessful() throws SQLException {
        int id = 3;
        String name = "name updated";

        Assertions.assertTrue(authorService.update(id, name));
    }

}
