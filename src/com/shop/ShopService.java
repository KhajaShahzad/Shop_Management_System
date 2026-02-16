package com.shop;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class ShopService {

    private Map<String, Product> products = new HashMap<>();
    private List<Sale> sales = new ArrayList<>();
    private List<Expense> expenses = new ArrayList<>();

    public void addProduct(Product product) {
        products.put(product.getBarcode(), product);
    }

    public void sellProduct(String barcode, int qty) {
        Product product = products.get(barcode);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        product.reduceStock(qty);
        double profit = product.getProfitPerUnit() * qty;
        sales.add(new Sale(barcode, qty, profit));
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public double getNetProfit() {
        double salesProfit =
                sales.stream().mapToDouble(Sale::getProfit).sum();
        double expenseTotal =
                expenses.stream().mapToDouble(Expense::getAmount).sum();
        return salesProfit - expenseTotal;
    }
    public double getTodayProfit() {
        LocalDate today = LocalDate.now();
        return sales.stream()
                .filter(s -> s.getSaleTime().toLocalDate().equals(today))
                .mapToDouble(Sale::getProfit)
                .sum();
    }

    public double getMonthlyProfit() {
        YearMonth currentMonth = YearMonth.now();
        return sales.stream()
                .filter(s -> YearMonth.from(s.getSaleTime()).equals(currentMonth))
                .mapToDouble(Sale::getProfit)
                .sum();
    }


}
