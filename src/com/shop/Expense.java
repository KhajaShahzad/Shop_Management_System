package com.shop;

import java.time.LocalDate;

public class Expense {

    private String type;
    private double amount;
    private LocalDate date;

    public Expense(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.date = LocalDate.now();
    }

    public double getAmount() {
        return amount;
    }
}
