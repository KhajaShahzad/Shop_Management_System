package com.shop;

public class SaleItem {

    private String barcode;
    private int quantity;
    private double profit;

    public SaleItem(String barcode, int quantity, double profit) {
        this.barcode = barcode;
        this.quantity = quantity;
        this.profit = profit;
    }

    public String getBarcode() {
        return barcode;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getProfit() {
        return profit;
    }
}
