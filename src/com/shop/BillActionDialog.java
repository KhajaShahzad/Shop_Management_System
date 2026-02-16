package com.shop;

import javafx.scene.control.*;
import javafx.stage.Modality;

import java.awt.Desktop;
import java.io.File;

public class BillActionDialog {

    public static void show(String pdfPath) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bill Generated");
        alert.setHeaderText("Bill generated successfully ✅");
        alert.setContentText("Choose an action:");

        ButtonType printBtn =
                new ButtonType("🖨 Print Bill");
        ButtonType whatsappBtn =
                new ButtonType("📱 Share via WhatsApp");
        ButtonType closeBtn =
                new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(printBtn, whatsappBtn, closeBtn);
        alert.initModality(Modality.APPLICATION_MODAL);

        alert.showAndWait().ifPresent(response -> {
            try {
                if (response == printBtn) {
                    Desktop.getDesktop().print(new File(pdfPath));
                }
                else if (response == whatsappBtn) {
                    // Open WhatsApp Desktop
                    Desktop.getDesktop().open(
                            new File("C:\\Users\\" +
                                    System.getProperty("user.name") +
                                    "\\AppData\\Local\\WhatsApp\\WhatsApp.exe")
                    );

                    // Open File Explorer with PDF selected
                    Runtime.getRuntime().exec(
                            "explorer /select,\"" + pdfPath + "\""
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
