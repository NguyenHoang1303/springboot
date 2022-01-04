package com.product.api.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HandlerDate {
    private static String PARENT = "yyyy-MM-dd";

    public static LocalDate ConvertStringToLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PARENT);
        return LocalDate.parse(date, formatter);
    }

}
