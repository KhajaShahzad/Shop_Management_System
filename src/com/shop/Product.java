package com.shop;

public class Product {
    private String barcode;
    private String name;
    private double costPrice;
    private double sellingPrice;
    private int stockQuantity;

    public Product(String barcode, String name,
                   double costPrice, double sellingPrice, int stockQuantity) {
        this.barcode = barcode;
        this.name = name;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.stockQuantity = stockQuantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public double getProfitPerUnit() {
        return sellingPrice - costPrice;
    }

    public void reduceStock(int qty) {
        if (qty > stockQuantity) {
            throw new IllegalArgumentException("Not enough stock");
        }
        stockQuantity -= qty;

    }
    public void addStock(int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }
        stockQuantity += qty;
    }
    public String getName() {
        return name;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void updatePrices(double costPrice, double sellingPrice) {
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
    }
    public void setBarcode(String barcode){
        this.barcode = barcode;
    }
}
