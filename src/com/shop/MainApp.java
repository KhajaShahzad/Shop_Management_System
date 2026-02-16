package com.shop;

import java.util.List;

public class MainApp {

    public static void main(String[] args) {

        List<MonthlyReport> reports =
                MonthlyReportDAO.getCurrentYearMonthlyReport();

        String status = ShopHealthAnalyzer.analyze(reports);

        System.out.println("SHOP STATUS: " + status);
    }
}
