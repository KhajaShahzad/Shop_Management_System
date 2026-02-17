package com.shop;

import java.sql.*;

public class InvoiceDAO {

    // ✅ Your existing method (keep it)
    public static void saveInvoice(Invoice invoice) {

        String sql = """
            INSERT INTO invoices (bill_no, bill_date, total_amount)
            VALUES (?, ?, ?)
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, invoice.getBillNo());
            ps.setObject(2, invoice.getBillDate());
            ps.setDouble(3, invoice.getTotalAmount());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ⭐ NEW METHOD (ADD THIS)
    public static int saveInvoice(Connection conn, Invoice invoice) throws SQLException {

        String sql = """
            INSERT INTO invoices (bill_no, bill_date, total_amount)
            VALUES (?, ?, ?)
        """;

        PreparedStatement ps =
                conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, invoice.getBillNo());
        ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
        ps.setDouble(3, invoice.getTotalAmount());

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {
            return rs.getInt(1);
        }

        return -1;
    }
}
