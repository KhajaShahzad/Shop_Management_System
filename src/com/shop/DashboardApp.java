package com.shop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import java.time.Year;


import java.util.List;

public class DashboardApp extends Application {
    private VBox createKpiCard(String title, double value, String color) {

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 13px;");

        Label valueLabel = new Label("₹ " + String.format("%.2f", value));
        valueLabel.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: " + color + ";"
        );

        VBox box = new VBox(5, titleLabel, valueLabel);
        box.setStyle(
                "-fx-padding: 10;" +
                        "-fx-border-color: #dddddd;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-color: #ffffff;"
        );

        return box;

    }



    @Override
    public void start(Stage stage) {
        ComboBox<String> monthBox = new ComboBox<>();
        monthBox.getItems().addAll(
                "January","February","March","April","May","June",
                "July","August","September","October","November","December"
        );
        monthBox.setValue("January");

        ComboBox<Integer> yearBox = new ComboBox<>();
        yearBox.getItems().addAll(2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032, 2033, 2034, 2035);
        yearBox.setValue(2026);

        HBox filterBar = new HBox(10,
                new Label("Month:"), monthBox,
                new Label("Year:"), yearBox
        );


        // 1️⃣ Fetch data FIRST
        int selectedMonth = monthBox.getSelectionModel().getSelectedIndex() + 1;
        int selectedYear = yearBox.getValue();

        List<MonthlyReport> reports =
                MonthlyReportDAO.getMonthlyReport(selectedMonth, selectedYear);

        // 2️⃣ Analyze shop health
        String status = ShopHealthAnalyzer.analyze(reports);

        // 3️⃣ Shop name
        Label shopNameLabel = new Label("Galaxy Home Needs");
        shopNameLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        // 4️⃣ Status label
        Label statusLabel = new Label("SHOP STATUS: " + status);
        statusLabel.setStyle(getStatusStyle(status));

        // 5️⃣ Charts
        LineChart<String, Number> salesChart =
                createLineChart("Monthly Sales Profit", reports, true);

        LineChart<String, Number> expenseChart =
                createLineChart("Monthly Expenses", reports, false);

        BarChart<String, Number> netProfitChart =
                createNetProfitChart(reports);

        // 6️⃣ INSIGHTS (NOW reports exists)
        VBox insightBox = new VBox(8);
        insightBox.setStyle(
                "-fx-padding: 12;" +
                        "-fx-border-color: #cccccc;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-color: #f9f9f9;"
        );
        String growthText = GrowthCalculator.calculateGrowth(reports);

        Label growthLabel = new Label(growthText);
        growthLabel.setStyle(getGrowthStyle(growthText));
        double totalSales = reports.stream()
                .mapToDouble(MonthlyReport::getSalesProfit)
                .sum();

        double totalExpenses = reports.stream()
                .mapToDouble(MonthlyReport::getExpenses)
                .sum();

        double netProfit = totalSales - totalExpenses;







// Section title
        Label insightTitle = new Label("📊 Business Insights");
        insightTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        insightBox.getChildren().add(insightTitle);


        List<String> insights =
                InsightEngine.generateInsights(reports);

        for (String text : insights) {
            Label insightLabel = new Label("• " + text);
            insightLabel.setStyle(
                    "-fx-font-size: 13px;" +
                            "-fx-padding: 3 0 3 5;"
            );
            insightBox.getChildren().add(insightLabel);
        }
        HBox kpiBar = new HBox(15,
                createKpiCard("💰 Total Sales", totalSales, "green"),
                createKpiCard("💸 Total Expenses", totalExpenses, "red"),
                createKpiCard("🧾 Net Profit", netProfit, netProfit >= 0 ? "green" : "red")
        );




        // 7️⃣ Layout
        VBox root = new VBox(15,
                shopNameLabel,
                filterBar,
                statusLabel,
                growthLabel,
                kpiBar,
                salesChart,
                expenseChart,
                netProfitChart,
                insightBox
        );




        stage.setTitle("Shop Analytics Dashboard");
        stage.setScene(new Scene(root, 900, 750));
        stage.show();
    }

    // 🎨 Status color logic
    private String getStatusStyle(String status) {
        if (status.contains("PROFIT")) {
            return "-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;";
        } else if (status.contains("LOSS")) {
            return "-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;";
        } else {
            return "-fx-text-fill: orange; -fx-font-size: 14px; -fx-font-weight: bold;";
        }
    }

    private LineChart<String, Number> createLineChart(
            String title, List<MonthlyReport> reports, boolean sales) {

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle(title);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (MonthlyReport r : reports) {
            String month = "Month " + r.getMonth();
            double value = sales ? r.getSalesProfit() : r.getExpenses();
            series.getData().add(new XYChart.Data<>(month, value));
        }

        chart.getData().add(series);
        return chart;
    }

    private BarChart<String, Number> createNetProfitChart(
            List<MonthlyReport> reports) {

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Monthly Net Profit");

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (MonthlyReport r : reports) {
            series.getData().add(
                    new XYChart.Data<>("Month " + r.getMonth(), r.getNetProfit()));
        }

        chart.getData().add(series);
        return chart;
    }

    public static void main(String[] args) {
        launch(args);
    }
    private String getGrowthStyle(String text) {
        if (text.contains("📈")) {
            return "-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;";
        } else if (text.contains("📉")) {
            return "-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;";
        } else {
            return "-fx-text-fill: gray; -fx-font-size: 13px;";
        }

    }
    private List<MonthlyReport> loadReports(
            ComboBox<String> monthBox,
            ComboBox<Integer> yearBox
    ) {
        int month = monthBox.getSelectionModel().getSelectedIndex() + 1;
        int year = yearBox.getValue();
        return MonthlyReportDAO.getMonthlyReport(month, year);
    }


}
