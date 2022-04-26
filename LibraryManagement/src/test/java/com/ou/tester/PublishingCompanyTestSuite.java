/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ou.tester;

import com.ou.pojo.PublishingCompany;
import com.ou.services.PublishingCompanyService;
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
public class PublishingCompanyTestSuite {
    private static Connection conn;
    private static PublishingCompanyService pubs;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(UserTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }

        pubs = new PublishingCompanyService();
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
    public void testPublishingCompanyNameNotNull() {
        try {
            List<PublishingCompany> publishingCompanys = pubs.getPublishingCompanys(null);
            publishingCompanys.forEach(d -> Assertions.assertNotNull(d.getName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPublishingCompanyNameUnique() {
        try {
            List<PublishingCompany> publishingCompanys = pubs.getPublishingCompanys(null);
            List<String> listTest = publishingCompanys.stream().flatMap(d -> Stream.of(d.getName())).collect(Collectors.toList());
            Set<String> others = new HashSet<>(listTest);
            Assertions.assertEquals(publishingCompanys.size(), others.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchSuccessful() throws SQLException {
        String kw = "m";
        List<PublishingCompany> publishingCompanys = pubs.getPublishingCompanys(kw);

        for (PublishingCompany p : publishingCompanys) {
            Assertions.assertTrue(p.getName().toLowerCase().contains(kw.toLowerCase()));
        }
    }

    @Test
    public void testSearchInvalid() throws SQLException {
        String kw = "wwwww";
        List<PublishingCompany> publishingCompanys = pubs.getPublishingCompanys(kw);
        Assertions.assertEquals(publishingCompanys.size(), 0);
    }

    @Test
    public void testSearchUnscure() throws SQLException {
        String kw = "1 OR 1=1";
        List<PublishingCompany> name = pubs.getPublishingCompanys(kw);

        Assertions.assertEquals(name.size(), 0);
    }

    @Test
    public void deleteFail() throws SQLException {
        int id = 999999999;
        Assertions.assertFalse(pubs.deletePublishingCompany(id));
    }

    @Test
    public void deleteSuccess() throws SQLException {
        try {
            PublishingCompany p = new PublishingCompany();
            p.setName("Test");
            pubs.addPublishingCompany(p);
            int id = pubs.getPublishingCompanys("Test").get(0).getId();
            Assertions.assertTrue(pubs.deletePublishingCompany(id));
            Assertions.assertNull(pubs.getPublishingCompanyById(id));
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Test
    public void testAddSuccess() throws SQLException {
        PublishingCompany p = new PublishingCompany();
        p.setName("TestingForPublishingCompany1");
        Assertions.assertTrue(pubs.addPublishingCompany(p));

        PublishingCompany CTest = pubs.getPublishingCompanys("TestingForPublishingCompany1").get(0);
        Assertions.assertEquals(p.getName(), CTest.getName());
    }

    @Test
    public void testAddFailWithExist() throws SQLException {
        List<PublishingCompany> publishingCompanys = pubs.getPublishingCompanys(null);
        PublishingCompany p = new PublishingCompany();
        //Set cho publishingCompany mới có tên giống với tên của 1 department trong db
        p.setName(publishingCompanys.get(0).getName());
        Assertions.assertFalse(pubs.addPublishingCompany(p));
    }

    @Test
    public void testUpdateSuccess() throws SQLException {
        PublishingCompany publishingCompany = pubs.getPublishingCompanyById(5);
        String name = publishingCompany.getName();
        System.out.println(name);
        //Gán cho department có id là 8 trong db một tên chưa từng tồn tại
        Assertions.assertTrue(pubs.updateDepartment(5, "TestUpdate5"));
        //Lấy ra tên của department sau update
        String nameAfter = pubs.getPublishingCompanyById(5).getName();
        System.out.println(nameAfter);
        //Kiểm tra tên của department đã thay đổi khác với tên ban đầu
        Assertions.assertNotEquals(name, nameAfter);
    }

    @Test
    public void testUpdateFailWithExist() throws SQLException {
        List<PublishingCompany> publishingCompanys = pubs.getPublishingCompanys(null);
        //Gán cho publishingCompany thứ 1 trong danh sách bằng tên của department thứ 2 trong danh sách
        Assertions.assertFalse(pubs.updateDepartment(publishingCompanys.get(0).getId(), publishingCompanys.get(1).getName()));
        //Sửa thông tin một publishingCompany có id không tồn tại
        Assertions.assertFalse(pubs.updateDepartment(111111, "ddddddddddd"));
    }
}
