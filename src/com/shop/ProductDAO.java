package com.shop;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductDAO {

    public static Product findByBarcode(String barcode) {

        String sql = "SELECT * FROM products WHERE barcode = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, barcode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getString("barcode"),
                        rs.getString("name"),
                        rs.getDouble("cost_price"),
                        rs.getDouble("selling_price"),
                        rs.getInt("stock_quantity")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void saveProduct(Product p) {

        String barcode = BarcodeUtil.generate();
        p.setBarcode(barcode);

        String sql = "INSERT INTO products VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getBarcode());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getCostPrice());
            ps.setDouble(4, p.getSellingPrice());
            ps.setInt(5, p.getStockQuantity());

            ps.executeUpdate();

            BarcodeGenerator.generateCode128(barcode);
            System.out.println("Product saved with barcode: " + barcode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
