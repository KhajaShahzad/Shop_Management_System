package com.shop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BillingApp extends Application {

    @Override
    public void start(Stage stage) {

        Label title = new Label("🧾 Billing / POS");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TextField barcodeField = new TextField();
        barcodeField.setPromptText("Scan barcode here...");
        barcodeField.setPrefWidth(300);

        barcodeField.setOnAction(e -> {
            String scannedBarcode = barcodeField.getText().trim();

            if (!scannedBarcode.isEmpty()) {
                handleScan(scannedBarcode);
                barcodeField.clear();
            }
        });

        VBox root = new VBox(15, title, barcodeField);
        root.setStyle("-fx-padding: 20;");

        stage.setTitle("Galaxy Home Needs - Billing");
        stage.setScene(new Scene(root, 400, 200));
        stage.show();
    }

    private void handleScan(String barcode) {
        Product product = ProductDAO.findByBarcode(barcode);

        if (product == null) {
            System.out.println("❌ Product not found for barcode: " + barcode);
            return;
        }

        System.out.println("✅ Added to bill: " + product.getName());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
