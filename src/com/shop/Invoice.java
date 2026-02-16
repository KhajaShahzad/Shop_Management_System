package com.shop;

import java.time.LocalDateTime;

public class Invoice {
    private String billNo;
    private LocalDateTime billDate;
    private double totalAmount;

    public Invoice(String billNo, double totalAmount) {
        this.billNo = billNo;
        this.billDate = LocalDateTime.now();
        this.totalAmount = totalAmount;
    }

    public String getBillNo() {
        return billNo;
    }

    public LocalDateTime getBillDate() {
        return billDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}
