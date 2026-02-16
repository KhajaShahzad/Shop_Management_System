package com.shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

public class SaleDAO {

    public static void saveSale(String barcode, int quantity, double profit) {

        String sql =
                "INSERT INTO sales (barcode, quantity_sold, profit, sale_time) " +
                        "VALUES (?, ?, ?, ?)";


        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, barcode);
            ps.setInt(2, quantity);
            ps.setDouble(3, profit);
            ps.setObject(4, LocalDateTime.now());

            ps.executeUpdate();
            System.out.println("Sale inserted into MySQL");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
