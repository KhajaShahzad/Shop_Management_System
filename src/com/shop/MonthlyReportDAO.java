package com.shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonthlyReportDAO {

    // ✅ MONTH + YEAR FILTER (CLEAN & CORRECT)
    public static List<MonthlyReport> getMonthlyReport(int month, int year) {

        List<MonthlyReport> reports = new ArrayList<>();

        String sql = """
            SELECT 
                ? AS month,
                IFNULL(SUM(s.profit), 0) AS sales_profit,
                (
                    SELECT IFNULL(SUM(e.amount), 0)
                    FROM shop_expenses e
                    WHERE MONTH(e.expense_date) = ?
                      AND YEAR(e.expense_date) = ?
                ) AS expenses
            FROM sales s
            WHERE MONTH(s.sale_time) = ?
              AND YEAR(s.sale_time) = ?
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, month);
            ps.setInt(2, month);
            ps.setInt(3, year);
            ps.setInt(4, month);
            ps.setInt(5, year);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double sales = rs.getDouble("sales_profit");
                double expenses = rs.getDouble("expenses");

                reports.add(new MonthlyReport(month, sales, expenses));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reports;
    }

    // ✅ FULL YEAR AGGREGATION (UNCHANGED, CORRECT)
    public static List<MonthlyReport> getCurrentYearMonthlyReport() {

        Map<Integer, MonthlyReport> reportMap = new HashMap<>();

        String salesSql =
                "SELECT MONTH(sale_time) AS month, SUM(profit) AS total_profit " +
                        "FROM sales " +
                        "WHERE YEAR(sale_time) = YEAR(CURDATE()) " +
                        "GROUP BY MONTH(sale_time)";

        String expenseSql =
                "SELECT MONTH(expense_date) AS month, SUM(amount) AS total_expense " +
                        "FROM shop_expenses " +
                        "WHERE YEAR(expense_date) = YEAR(CURDATE()) " +
                        "GROUP BY MONTH(expense_date)";

        try (Connection con = DBConnection.getConnection()) {

            try (PreparedStatement ps = con.prepareStatement(salesSql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int month = rs.getInt("month");
                    double profit = rs.getDouble("total_profit");

                    reportMap
                            .computeIfAbsent(month, MonthlyReport::new)
                            .setSalesProfit(profit);
                }
            }

            try (PreparedStatement ps = con.prepareStatement(expenseSql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    int month = rs.getInt("month");
                    double expense = rs.getDouble("total_expense");

                    reportMap
                            .computeIfAbsent(month, MonthlyReport::new)
                            .setExpenses(expense);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(reportMap.values());
    }
}
