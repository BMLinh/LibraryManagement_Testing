package com.ou.services;

import com.ou.pojo.Stat;
import com.ou.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatService {
    public List<Stat> getStat(Map<String, String> params) throws SQLException {
        List<Stat> stats = new ArrayList<>();
        try(Connection conn = JdbcUtils.getConn()){
            String sql = "select month(created_date), b.name, sum(br.amount) from borrowingbook br, book b\n" +
                    "where br.book_id = b.id";
            String sqlSort = "group by month(created_date), b.name\n" +
                    "order by month(created_date)";
            if (params != null) {
                if (params.containsKey("quarter")) {
                    String sqlRun = sql + " and quarter(created_date) = ?" + sqlSort;
                    PreparedStatement stm = conn.prepareStatement(sqlRun);
                    stm.setString(1, params.get("quarter"));
                    ResultSet rs = stm.executeQuery();
                    while (rs.next()) {
                        int month = rs.getInt("month(created_date)");
                        String bookName = rs.getString("b.name");
                        int sum = rs.getInt("sum(br.amount)");
                        stats.add(new Stat(month, bookName, sum));
                    }
                }
                if (params.containsKey("year")){
                    String sqlRun = sql + " and year(created_date) = ?" + sqlSort;
                    PreparedStatement stm = conn.prepareStatement(sqlRun);
                    stm.setString(1, params.get("year"));
                    ResultSet rs = stm.executeQuery();
                    while (rs.next()) {
                        int month = rs.getInt("month(created_date)");
                        String bookName = rs.getString("b.name");
                        int sum = rs.getInt("sum(br.amount)");
                        stats.add(new Stat(month, bookName, sum));
                    }
                }
            }
        }
        return stats;
    }
}
