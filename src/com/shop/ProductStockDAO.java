package com.shop;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProductStockDAO {

    public static void reduceStock(String barcode, int quantity) {

        String sql =
                "UPDATE products SET stock_quantity = stock_quantity - ? " +
                        "WHERE barcode = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, quantity);
            ps.setString(2, barcode);

            ps.executeUpdate();
            System.out.println("Stock updated in MySQL");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
