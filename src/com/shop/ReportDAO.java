package com.shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportDAO {

    public static double getTodayProfit() {

        String sql =
                "SELECT SUM(profit) FROM sales WHERE DATE(sale_time) = CURDATE()";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static double getMonthlyProfit() {

        String sql =
                "SELECT SUM(profit) FROM sales " +
                        "WHERE YEAR(sale_time) = YEAR(CURDATE()) " +
                        "AND MONTH(sale_time) = MONTH(CURDATE())";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
