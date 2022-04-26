/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou.tester;

import com.ou.pojo.BookCategory;
import com.ou.services.BookCategoryService;
import com.ou.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Linh
 */
public class BookCategoryTestSuite {
    private static Connection conn;
    private static BookCategoryService bookCatg;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(UserTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }

        bookCatg = new BookCategoryService();
    }

    @AfterAll
    public static void afterAll() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserTestSuite.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Test
    public void testBookCategoryNameNotNull() {
        try {
            List<BookCategory> bookCategorys = bookCatg.getBookCategory(null);
            bookCategorys.forEach(d -> Assertions.assertNotNull(d.getName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testSearchSuccessful() throws SQLException {
        String kw = "m";
        List<BookCategory> bookCategorys = bookCatg.getBookCategory(kw);

        for (BookCategory b : bookCategorys) {
            Assertions.assertTrue(b.getName().toLowerCase().contains(kw.toLowerCase()));
        }
    }

    @Test
    public void testSearchInvalid() throws SQLException {
        String kw = "wwwww";
        List<BookCategory> bookCategorys = bookCatg.getBookCategory(kw);
        Assertions.assertEquals(bookCategorys.size(), 0);
    }

    @Test
    public void testSearchUnscure() throws SQLException {
        String kw = "1 OR 1=1";
        List<BookCategory> name = bookCatg.getBookCategory(kw);

        Assertions.assertEquals(name.size(), 0);
    }

    @Test
    public void deleteFail() throws SQLException {
        int id = 999999999;
        Assertions.assertFalse(bookCatg.deleteBookCategory(id));
    }

    @Test
    public void deleteSuccess() throws SQLException {
        try {
            BookCategory b = new BookCategory();
            b.setName("Test");
            b.setPosition("Dãy Test");
            bookCatg.addBookCategory(b);
            int id = bookCatg.getBookCategory("Test").get(0).getId();
            Assertions.assertTrue(bookCatg.deleteBookCategory(id));
            Assertions.assertNull(bookCatg.getBookCategoryById(id));
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Test
    public void testAddSuccess() throws SQLException {
        BookCategory b = new BookCategory();
        b.setName("TestingBookCategory");
        b.setPosition("TestPositon");
        Assertions.assertTrue(bookCatg.addBookCategory(b));

        BookCategory CTest = bookCatg.getBookCategory("TestingBookCategory").get(0);
        Assertions.assertEquals(b.getName(), CTest.getName());
    }

    @Test
    public void testUpdateSuccess() throws SQLException {
        BookCategory bookCategory = bookCatg.getBookCategoryById(6);
        String name = bookCategory.getName();
        System.out.println(name);
        //Gán cho bookCategory có id là 6 trong db một tên chưa từng tồn tại
        Assertions.assertTrue(bookCatg.updateBookCategory(6, "TestUpdate6", bookCategory.getPosition()));
        //Lấy ra tên của bookCategory sau update
        String nameAfter = bookCatg.getBookCategoryById(6).getName();
        System.out.println(nameAfter);
        //Kiểm tra tên của bookCategory đã thay đổi khác với tên ban đầu
        Assertions.assertNotEquals(name, nameAfter);
    }

    @Test
    public void testUpdateFail() throws SQLException {
        //Sửa thông tin một bookCategory có id không tồn tại
        Assertions.assertFalse(bookCatg.updateBookCategory(111111, "ddddddddddd", "aaaaaaaaaaaaaaa"));
    }
}
