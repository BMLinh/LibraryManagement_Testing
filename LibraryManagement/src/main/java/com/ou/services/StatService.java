package com.ou.services;

import com.ou.pojo.Stat;
import com.ou.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatService {
    public List<Stat> getStat(int quarter, int year) throws SQLException {
        List<Stat> stats = new ArrayList<>();
        try(Connection conn = JdbcUtils.getConn()){
            String sql = "select month(created_date), year(created_date), b.name, sum(br.amount) from borrowingbook br, book b\n" +
                    "where br.book_id = b.id and quarter(created_date) = ? and year(created_date) = ? group by month(created_date), year(created_date),  b.name " +
                    "order by month(created_date)";
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, quarter);
            stm.setInt(2, year);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int month = rs.getInt("month(created_date)");
                int year1 = rs.getInt("year(created_date)");
                String bookName = rs.getString("b.name");
                int sum = rs.getInt("sum(br.amount)");
                stats.add(new Stat(month,year1, bookName, sum));
            }

        }
        return stats;
    }
}
