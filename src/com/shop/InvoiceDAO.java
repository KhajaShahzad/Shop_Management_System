package com.shop;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class InvoiceDAO {

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
}
