package com.shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class ShopExpenseDAO {

    public static void addExpense(String type, double amount) {

        String sql =
                "INSERT INTO shop_expenses (type, amount, expense_date) VALUES (?, ?, ?)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, type);
            ps.setDouble(2, amount);
            ps.setDate(3, java.sql.Date.valueOf(LocalDate.now()));

            ps.executeUpdate();
            System.out.println("Expense added");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
