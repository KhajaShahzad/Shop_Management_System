package com.shop;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BarcodeGenerator {

    public static void generateCode128(String barcodeValue) {
        try {
            int width = 300;
            int height = 100;

            BitMatrix matrix = new MultiFormatWriter()
                    .encode(barcodeValue, BarcodeFormat.CODE_128, width, height);

            Path path = Paths.get(
                    "barcodes/" + barcodeValue + ".png"
            );

            MatrixToImageWriter.writeToPath(matrix, "PNG", path);

            System.out.println("Barcode generated: " + path.toAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
