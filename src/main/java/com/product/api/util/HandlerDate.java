package com.product.api.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class HandlerDate {
    public static String PARENT = "yyyy-MM-dd";
    public static String PARENT_DETAIL = "yyyy-MM-dd HH:mm:ss";

    public static LocalDate convertStringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PARENT);
        return LocalDate.parse(date, formatter);
    }

    public static LocalDate getCurrentTimeDetail() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PARENT);
        String date = LocalDateTime.now().format(formatter);
        return LocalDate.parse(date, formatter);
    }


}
