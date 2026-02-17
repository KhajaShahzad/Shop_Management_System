package com.shop;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReceiptPdfGenerator {

    // 80mm width receipt
    private static final float RECEIPT_WIDTH = 226f;

    public static String generateReceipt(
            String billNo,
            List<SaleItem> items,
            double totalAmount
    ) {

        String filePath = "receipts/receipt_" + billNo + ".pdf";

        try {
            Rectangle pageSize = new Rectangle(RECEIPT_WIDTH, 800);
            Document document = new Document(pageSize, 10, 10, 10, 10);

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 9);
            Font boldFont = new Font(Font.HELVETICA, 9, Font.BOLD);

            // Shop name
            Paragraph shop = new Paragraph("GALAXY HOME NEEDS\n", titleFont);
            shop.setAlignment(Element.ALIGN_CENTER);
            document.add(shop);

            document.add(new Paragraph("-----------------------------"));

            // Items
            // Items
            for (SaleItem item : items) {
                Paragraph p = new Paragraph(
                        "Product: " + item.getBarcode() +
                                "  x" + item.getQuantity() +
                                "  Profit: ₹" + item.getProfit(),
                        normalFont
                );
                document.add(p);
            }


            document.add(new Paragraph("-----------------------------"));

            // Total
            Paragraph total = new Paragraph(
                    "TOTAL : ₹ " + totalAmount,
                    boldFont
            );
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.add(new Paragraph("\n"));

            // Footer
            document.add(new Paragraph(
                    "Bill No: " + billNo,
                    normalFont
            ));
            document.add(new Paragraph(
                    "Thank you! Visit again 😊",
                    normalFont
            ));

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return filePath;
    }
}
