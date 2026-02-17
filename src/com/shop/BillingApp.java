package com.shop;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BillingApp extends Application {

    private ObservableList<BillItem> billItems =
            FXCollections.observableArrayList();

    private Label totalLabel = new Label("Total: ₹ 0");

    @Override
    public void start(Stage stage) {

        Label title = new Label("🧾 Billing / POS");
        title.setStyle("-fx-font-size:18px;-fx-font-weight:bold;");

        TextField barcodeField = new TextField();
        barcodeField.setPromptText("Scan barcode here...");
        barcodeField.requestFocus();

        TableView<BillItem> table = createTable();

        // 🔥 When barcode entered
        barcodeField.setOnAction(e -> {

            String barcode = barcodeField.getText().trim();
            barcodeField.clear();

            Product product = ProductDAO.findByBarcode(barcode);

            if (product == null) {
                showAlert("Product not found!");
                return;
            }

            addToBill(product);
            updateTotal();
        });

        VBox root = new VBox(10, title, barcodeField, table, totalLabel);
        root.setStyle("-fx-padding:15;");

        stage.setTitle("Galaxy Home Needs - Billing");
        stage.setScene(new Scene(root, 500, 500));
        stage.show();
    }

    // ================= TABLE =================
    private TableView<BillItem> createTable() {

        TableView<BillItem> table = new TableView<>(billItems);

        TableColumn<BillItem, String> nameCol = new TableColumn<>("Item");
        nameCol.setCellValueFactory(
                c -> new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getName()));

        TableColumn<BillItem, Number> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(
                c -> new javafx.beans.property.SimpleDoubleProperty(
                        c.getValue().getPrice()));

        TableColumn<BillItem, Number> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(
                c -> new javafx.beans.property.SimpleIntegerProperty(
                        c.getValue().getQuantity()));

        TableColumn<BillItem, Number> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(
                c -> new javafx.beans.property.SimpleDoubleProperty(
                        c.getValue().getTotal()));

        table.getColumns().addAll(nameCol, priceCol, qtyCol, totalCol);

        return table;
    }

    // ================= ADD PRODUCT =================
    private void addToBill(Product product) {

        for (BillItem item : billItems) {
            if (item.getName().equals(product.getName())) {
                item.increaseQty();
                return;
            }
        }

        billItems.add(new BillItem(
                product.getName(),
                product.getSellingPrice()
        ));
    }

    // ================= TOTAL =================
    private void updateTotal() {
        double total = billItems.stream()
                .mapToDouble(BillItem::getTotal)
                .sum();

        totalLabel.setText("Total: ₹ " + total);
    }

    private void showAlert(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
