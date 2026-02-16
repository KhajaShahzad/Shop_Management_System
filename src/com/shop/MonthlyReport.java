package com.shop;

public class MonthlyReport {

    private int month;
    private double salesProfit;
    private double expenses;

    // ✅ REQUIRED CONSTRUCTOR
    public MonthlyReport(int month, double salesProfit, double expenses) {
        this.month = month;
        this.salesProfit = salesProfit;
        this.expenses = expenses;
    }

    // Existing constructor (keep it)
    public MonthlyReport(int month) {
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public double getSalesProfit() {
        return salesProfit;
    }

    public void setSalesProfit(double salesProfit) {
        this.salesProfit = salesProfit;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }

    public double getNetProfit() {
        return salesProfit - expenses;
    }
}
