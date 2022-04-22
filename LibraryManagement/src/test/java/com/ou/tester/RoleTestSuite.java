package com.ou.tester;

import com.ou.pojo.Role;
import com.ou.services.RoleService;
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

public class RoleTestSuite {
    private static Connection conn;
    private static RoleService s;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(UserTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }

        s = new RoleService();
    }

    @AfterAll
    public static void afterAll() {
        if(conn != null)
            try{
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserTestSuite.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    @Test
    public void testRoleNameNotNull(){
        try {
            List<Role> roles = s.getRoles(null);
            roles.forEach(r -> Assertions.assertNotNull(r.getName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRoleNameUnique() throws SQLException {
        List<Role> roles = s.getRoles(null);

        List<String> nameRoles = roles.stream().flatMap(r -> Stream.of(r.getName())).collect(Collectors.toList());
        Set<String> others = new HashSet<>(nameRoles);

        Assertions.assertEquals(nameRoles.size(), others.size());
    }

    @Test
    public void testSearchSuccessful() throws SQLException{
        String kw = "n";
        List<Role> roles = s.getRoles(kw);

        for (Role rl: roles)
            Assertions.assertTrue(rl.getName().toLowerCase().contains(kw.toLowerCase()));
    }

    @Test
    public void testSearchInvalid() throws SQLException{
        String kw = "nnnnnnnnnnnnnnnnn";
        List<Role> roles = s.getRoles(kw);

        Assertions.assertEquals(roles.size(), 0);
    }

    @Test
    public void testSearchUnscure() throws SQLException {
        String kw = "1 OR 1=1";
        List<Role> roles = s.getRoles(kw);

        Assertions.assertEquals(roles.size(), 0);
    }

    @Test
    public void deleteFail() throws SQLException {
        int id = 999999999;
        Assertions.assertFalse(s.deleteRole(id));
    }

    @Test
    public void deleteSuccess() throws SQLException {
        //Tạo mới 1 role và tiến hành xóa nó
        Role role = new Role();
        role.setName("Test");
        s.addRole(role);
        int id = s.getRoles("Test").get(0).getId();
        System.out.println(id);
        Assertions.assertTrue(s.deleteRole(id));
        Assertions.assertNull(s.getRoleById(id));
    }

    @Test
    public void testAddSuccess() throws SQLException {
        Role r = new Role();
        r.setName("MMMMMMMMM12");
        Assertions.assertTrue(s.addRole(r));

        Role rTest = s.getRoles("MMMMMMMMM12").get(0);
        Assertions.assertEquals(r.getName(),rTest.getName());
    }

    @Test
    public void testAddFailWithExist() throws SQLException{
        List<Role> roles = s.getRoles(null);
        Role r = new Role();
        //Set cho role mới có tên giống với tên của 1 role trong db
        r.setName(roles.get(0).getName());
        Assertions.assertFalse(s.addRole(r));
    }

    @Test
    public void testUpdateSuccess() throws SQLException{
        Role role = s.getRoleById(8);
        String name = role.getName();
        System.out.println(name);
        //Gán cho role có id là 8 trong db một tên chưa từng tồn tại
        Assertions.assertTrue(s.updateRole(8, "TestUpdate4"));
        //Lấy ra tên của role sau update
        String nameAfter = s.getRoleById(8).getName();
        System.out.println(nameAfter);
        //Kiểm tra tên của role đã thay đổi khác với tên ban đầu
        Assertions.assertNotEquals(name, nameAfter);
    }

    @Test
    public void testUpdateFailWithExist() throws  SQLException{
        List<Role> roles = s.getRoles(null);
        //Gán cho role thứ 1 trong danh sách bằng tên của role thứ 2 trong danh sách
        Assertions.assertFalse(s.updateRole(roles.get(0).getId(), roles.get(1).getName()));
        //Sửa thông tin một role có id không tồn tại
        Assertions.assertFalse(s.updateRole(111111, "ddddddddddd"));
    }
}
