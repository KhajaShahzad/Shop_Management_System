package com.shop;

import java.time.LocalDateTime;

public class Sale {

    private String barcode;
    private int quantitySold;
    private double profit;
    private LocalDateTime saleTime;

    public Sale(String barcode, int quantitySold, double profit) {
        this.barcode = barcode;
        this.quantitySold = quantitySold;
        this.profit = profit;
        this.saleTime = LocalDateTime.now();
    }

    public double getProfit() {
        return profit;
    }
    public LocalDateTime getSaleTime(){
        return saleTime;
    }
}
