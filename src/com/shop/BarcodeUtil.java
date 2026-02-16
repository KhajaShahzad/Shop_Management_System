package com.shop;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BarcodeUtil {

    public static String generate() {
        return "GHN-" + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
}
