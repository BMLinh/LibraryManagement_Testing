package com.ou.tester;

import com.ou.pojo.Department;
import com.ou.pojo.Role;
import com.ou.services.DepartmentService;
import com.ou.utils.JdbcUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DepartmentTestSuite {
    private static Connection conn;
    private static DepartmentService s;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(UserTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }

        s = new DepartmentService();
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
    public void testDepartmentNameNotNull() {
        try {
            List<Department> departments = s.getDepartments(null);
            departments.forEach(d -> Assertions.assertNotNull(d.getName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDepartmentNameUnique() {
        try {
            List<Department> departments = s.getDepartments(null);
            List<String> listTest = departments.stream().flatMap(d -> Stream.of(d.getName())).collect(Collectors.toList());
            Set<String> others = new HashSet<>(listTest);
            Assertions.assertEquals(departments.size(), others.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchSuccessful() throws SQLException {
        String kw = "c";
        List<Department> departments = s.getDepartments(kw);

        for (Department d : departments) {
            Assertions.assertTrue(d.getName().toLowerCase().contains(kw.toLowerCase()));
        }
    }

    @Test
    public void testSearchInvalid() throws SQLException {
        String kw = "wwwww";
        List<Department> departments = s.getDepartments(kw);
        Assertions.assertEquals(departments.size(), 0);
    }

    @Test
    public void testSearchUnscure() throws SQLException {
        String kw = "1 OR 1=1";
        List<Department> roles = s.getDepartments(kw);

        Assertions.assertEquals(roles.size(), 0);
    }

    @Test
    public void deleteFail() throws SQLException {
        int id = 999999999;
        Assertions.assertFalse(s.deleteDepartment(id));
    }

    @Test
    public void deleteSuccess() throws SQLException {
        try {
            Department d = new Department();
            d.setName("Test");
            s.addDepartment(d);
            int id = s.getDepartments("Test").get(0).getId();
            Assertions.assertTrue(s.deleteDepartment(id));
            Assertions.assertNull(s.getDepartmentById(id));
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Test
    public void testAddSuccess() throws SQLException {
        Department d = new Department();
        d.setName("MMMMMMMMM55");
        Assertions.assertTrue(s.addDepartment(d));

        Department rTest = s.getDepartments("MMMMMMMMM55").get(0);
        Assertions.assertEquals(d.getName(), rTest.getName());
    }

    @Test
    public void testAddFailWithExist() throws SQLException {
        List<Department> departments = s.getDepartments(null);
        Department d = new Department();
        //Set cho department mới có tên giống với tên của 1 role trong db
        d.setName(departments.get(0).getName());
        Assertions.assertFalse(s.addDepartment(d));
    }

    @Test
    public void testUpdateSuccess() throws SQLException {
        Department department = s.getDepartmentById(4);
        String name = department.getName();
        System.out.println(name);
        //Gán cho role có id là 8 trong db một tên chưa từng tồn tại
        Assertions.assertTrue(s.updateDepartment(4, "TestUpdate4"));
        //Lấy ra tên của role sau update
        String nameAfter = s.getDepartmentById(4).getName();
        System.out.println(nameAfter);
        //Kiểm tra tên của role đã thay đổi khác với tên ban đầu
        Assertions.assertNotEquals(name, nameAfter);
    }

    @Test
    public void testUpdateFailWithExist() throws SQLException {
        List<Department> roles = s.getDepartments(null);
        //Gán cho department thứ 1 trong danh sách bằng tên của role thứ 2 trong danh sách
        Assertions.assertFalse(s.updateDepartment(roles.get(0).getId(), roles.get(1).getName()));
        //Sửa thông tin một department có id không tồn tại
        Assertions.assertFalse(s.updateDepartment(111111, "ddddddddddd"));
    }
}
