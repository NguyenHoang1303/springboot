package com.product.api.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HandlerDateTest {


    @BeforeEach
    void setUp() {

    }

    @ParameterizedTest
    @ValueSource(strings = "2012-11-01")
    void convertStringToLocalDate(String date) {
        LocalDate localDate = HandlerDate.convertStringToLocalDate(date);
        LocalDate expected = LocalDate.of(2012, 11, 1);
        assertThat(localDate).isEqualTo(expected);
    }


    @Test
    void getCurrentTimeDetail() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(HandlerDate.PARENT);
        String date = LocalDateTime.now().format(formatter);
        LocalDate currentDate = HandlerDate.getCurrentTimeDetail();
        assertThat(currentDate).isEqualTo(date);
    }
}