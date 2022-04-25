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
        //Thống kê với quý có dữ liệu và quý thuộc 4 quý trong năm
        Map<String, String> paramQuarter = new HashMap<>();
        paramQuarter.put("quarter","4");
        Assertions.assertTrue(s.getStat(paramQuarter).size() > 0);

        //Thống kê với năm có dữ liệu
        Map<String, String> paramYear = new HashMap<>();
        paramYear.put("year","2022");
        Assertions.assertTrue(s.getStat(paramYear).size() > 0);
    }

    @Test
    public void getStatFail() throws SQLException{
        //Quý không thuộc 4 quý trong năm
        Map<String, String> paramQuarter = new HashMap<>();
        paramQuarter.put("quarter","6");
        Assertions.assertFalse(s.getStat(paramQuarter).size() > 0);

        //Thống kê với năm không có dữ liệu
        Map<String, String> paramYear = new HashMap<>();
        paramYear.put("year","20225");
        Assertions.assertFalse(s.getStat(paramYear).size() > 0);

        //Thống kê với năm không chính xác
        Map<String, String> paramYearErr = new HashMap<>();
        paramYear.put("year","-1");
        Assertions.assertFalse(s.getStat(paramYearErr).size() > 0);
    }
}
