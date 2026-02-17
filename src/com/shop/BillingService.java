package com.shop;

import java.sql.Connection;
import java.util.List;
import com.shop.InvoiceDAO;


public class BillingService {

    public boolean finalizeBill(Invoice invoice, List<SaleItem> items) {

        Connection conn = null;

        try {
            // get connection
            conn = DBConnection.getConnection();

            // start transaction
            conn.setAutoCommit(false);

            // TODO: save invoice
            // 1. Save invoice
            int invoiceId = InvoiceDAO.saveInvoice(conn, invoice);

            if (invoiceId <= 0) {
                throw new Exception("Invoice save failed");
            }

            System.out.println("Invoice saved with ID: " + invoiceId);

            // TODO: save sale items
            // TODO: reduce stock

            // commit if everything ok
            conn.commit();

            return true;

        } catch (Exception e) {

            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
            return false;

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
