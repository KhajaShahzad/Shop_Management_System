package com.shop;

import java.util.List;

public class GrowthCalculator {

    public static String calculateGrowth(List<MonthlyReport> reports) {

        if (reports.size() < 2) {
            return "⏳ Growth: Not enough data";
        }

        MonthlyReport last = reports.get(reports.size() - 1);
        MonthlyReport previous = reports.get(reports.size() - 2);

        double prevProfit = previous.getNetProfit();
        double currProfit = last.getNetProfit();

        if (prevProfit == 0) {
            return "⚠ Growth: Cannot calculate (previous profit is zero)";
        }

        double growth = ((currProfit - prevProfit) / Math.abs(prevProfit)) * 100;

        String arrow = growth >= 0 ? "📈" : "📉";
        return String.format("%s Growth: %.1f%% vs last month", arrow, growth);
    }
}
