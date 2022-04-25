package com.ou.tester;

import com.ou.services.RoleService;
import com.ou.services.StatService;
import com.ou.utils.JdbcUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatTestSuite {
    private static Connection conn;
    private static StatService s;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(UserTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }

        s = new StatService();
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
    public void getStatSuccessful() throws SQLException{
        //Thống kê với quý và năm có dữ liệu
        Assertions.assertTrue(s.getStat(1, 2022).size() > 0);
    }

    @Test
    public void getStatFail() throws SQLException{
        //Quý không thuộc 4 quý trong năm
        Assertions.assertFalse(s.getStat(6, 2022).size() > 0);

        //Thống kê với năm không có dữ liệu
        Assertions.assertFalse(s.getStat(1, 20225).size() > 0);

        //Thống kê với năm không chính xác
        Assertions.assertFalse(s.getStat(1, -1).size() > 0);

        //Thống kê với quý và năm không chính xác
        Assertions.assertFalse(s.getStat(-1, -1).size() > 0);

    }
}
