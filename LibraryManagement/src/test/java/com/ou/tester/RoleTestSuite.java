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


}
