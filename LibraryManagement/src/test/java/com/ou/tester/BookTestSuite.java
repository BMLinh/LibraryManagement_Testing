package com.ou.tester;

import com.ou.pojo.Book;
import com.ou.pojo.Department;
import com.ou.services.BookService;
import com.ou.utils.JdbcUtils;
import jdk.jfr.Name;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookTestSuite {
    private static Connection conn;
    private static BookService bookService;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(BookTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }

        bookService = new BookService();
    }

    @AfterAll
    public static void afterAll() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(BookTestSuite.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Test
    @Name("Tim kiem thanh cong")
    public void testSearchSuccessful() throws SQLException {
        String kw = "toan";
        List<Book> books = bookService.getBooks(kw);

        Assertions.assertTrue(books.size() > 0);
    }

    @Test
    @Name("Tim kiem khong ten sach ton tai")
    public void testSearchInvalid() throws SQLException {
        String kw = "qqqqqq";
        List<Book> books = bookService.getBooks(kw);

        Assertions.assertTrue(books.size() == 0);
    }

    @Test
    @Name("SQL Injection")
    public void testSearchUnscure() throws SQLException {
        String kw = "1 OR 1=1";
        List<Book> books = bookService.getBooks(kw);

        Assertions.assertEquals(books.size(), 0);
    }

    @Test
    public void deleteSuccessful() throws SQLException {
        int id = 8;
        Assertions.assertTrue(bookService.deleteBook(id));
    }

    @Test
    public void deleteFailed() throws SQLException {
        int id = 11111;
        Assertions.assertFalse(bookService.deleteBook(id));
    }

    @Test
    @Name("Them sach thanh cong")
    public void addSuccessful() throws SQLException {
        String name = "addSuccessful test";
        Book book = new Book();
        book.setName(name);
        book.setCategoryId(1);
        book.setPublishingCompanyId(1);
        Assertions.assertTrue(bookService.addBook(book));

        Book bookAdded = bookService.getBooks(name).get(0);
        Assertions.assertEquals(bookAdded.getName().
                compareToIgnoreCase(book.getName()), 0);
    }

    @Test
    public void updateSuccessful() throws SQLException {
        int id = 10;
        Map<String, String> pre = new HashMap<>();
        pre.put("name", "name updated");
        pre.put("descriptions", "descriptions updated");

        Assertions.assertTrue(bookService.updateBook(id, pre));
    }

}
