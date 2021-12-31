package com.product.api.repositories.enums;

import lombok.Getter;

@Getter
public enum Operation {
    EQUALITY(":"),
     LIKE ( "%"),
    GREATER_THAN_OR_EQUAL( ">="),
    LESS_THAN_OR_EQUAL("<="),
    EQUAL(":");

     final String value;

    Operation(String s) {
        this.value = s;
    }
}
