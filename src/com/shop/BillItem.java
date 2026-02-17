package com.shop;

public class BillItem {

    private String name;
    private double price;
    private int quantity;

    public BillItem(String name, double price) {
        this.name = name;
        this.price = price;
        this.quantity = 1;
    }

    public void increaseQty() {
        quantity++;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return price * quantity;
    }
}
