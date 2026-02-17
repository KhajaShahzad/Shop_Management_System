package com.shop;

import java.sql.*;
import java.time.LocalDateTime;

public class SaleDAO {

    // ⭐ NEW METHOD — used by BillingService (transaction safe)
    public static void saveSaleItem(Connection conn, SaleItem item, int invoiceId)
            throws SQLException {

        String sql = """
            INSERT INTO sales (barcode, quantity_sold, profit, sale_time, invoice_id)
            VALUES (?, ?, ?, ?, ?)
        """;

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, item.getBarcode());
        ps.setInt(2, item.getQuantity());
        ps.setDouble(3, item.getProfit());
        ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
        ps.setInt(5, invoiceId);

        ps.executeUpdate();
    }

    // ✅ OPTIONAL: your old method (keep if you use it elsewhere)
    public static void saveSale(String barcode, int quantity, double profit) {

        String sql = """
            INSERT INTO sales (barcode, quantity_sold, profit, sale_time)
            VALUES (?, ?, ?, ?)
        """;

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
