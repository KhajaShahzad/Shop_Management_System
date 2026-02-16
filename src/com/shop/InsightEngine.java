package com.shop;

import java.util.ArrayList;
import java.util.List;

public class InsightEngine {

    public static List<String> generateInsights(List<MonthlyReport> reports) {

        List<String> insights = new ArrayList<>();

        if (reports.size() < 2) {
            return List.of(
                    "Sales data available for only one month",
                    "Growth trend analysis requires at least 2 months",
                    "Current month indicates operational loss"
            );
        }


        MonthlyReport latest = reports.get(reports.size() - 1);
        MonthlyReport previous = reports.get(reports.size() - 2);

        // 1️⃣ Expense vs Sales ratio
        double expenseRatio =
                (latest.getExpenses() / latest.getSalesProfit()) * 100;

        if (expenseRatio > 80) {
            insights.add("🔴 Expenses consume " +
                    String.format("%.1f", expenseRatio) +
                    "% of sales. High risk.");
        } else if (expenseRatio > 50) {
            insights.add("🟠 Expenses consume " +
                    String.format("%.1f", expenseRatio) +
                    "% of sales. Needs control.");
        } else {
            insights.add("🟢 Expenses are under control (" +
                    String.format("%.1f", expenseRatio) + "%).");
        }

        // 2️⃣ Sales growth
        double growth =
                ((latest.getSalesProfit() - previous.getSalesProfit())
                        / previous.getSalesProfit()) * 100;

        if (growth > 0) {
            insights.add("📈 Sales grew by " +
                    String.format("%.1f", growth) + "% this month.");
        } else {
            insights.add("📉 Sales dropped by " +
                    String.format("%.1f", Math.abs(growth)) + "% this month.");
        }

        // 3️⃣ Profit/Loss insight
        if (latest.getNetProfit() > 0) {
            insights.add("Alhamdulilah 💰 Shop is profitable this month.");
        } else {
            insights.add("⚠ Shop is running at a loss this month.");
        }

        return insights;
    }
}
