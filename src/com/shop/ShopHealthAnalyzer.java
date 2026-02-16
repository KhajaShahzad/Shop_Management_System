package com.shop;

import java.util.List;

public class ShopHealthAnalyzer {

    public static String analyze(List<MonthlyReport> reports) {

        if (reports.isEmpty()) {
            return "No data available";
        }

        double totalSales = 0;
        double totalExpenses = 0;

        for (MonthlyReport r : reports) {
            totalSales += r.getSalesProfit();
            totalExpenses += r.getExpenses();
        }

        double netProfit = totalSales - totalExpenses;

        // PROFIT / LOSS
        if (netProfit < 0) {
            return "LOSS: Expenses are higher than sales";
        }

        // GROWTH CHECK (last 2 months)
        if (reports.size() >= 2) {
            MonthlyReport last = reports.get(reports.size() - 1);
            MonthlyReport prev = reports.get(reports.size() - 2);

            double growth =
                    ((last.getSalesProfit() - prev.getSalesProfit())
                            / prev.getSalesProfit()) * 100;

            if (growth > 10) {
                return "PROFIT & GROWING";
            } else if (growth < 0) {
                return "PROFIT BUT SALES DECLINING";
            }
        }

        return "PROFIT & STABLE";
    }
}
