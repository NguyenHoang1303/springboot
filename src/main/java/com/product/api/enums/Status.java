package com.product.api.enums;

import lombok.Getter;
import lombok.Setter;

@Getter

public enum Status {
    //Product
    SOLD_OUT("soldOut", 0),
    ON_SALE("onSale", 1),

    DELETE("delete", -1);

    private String key;
    private Integer value;

    Status(String key, int value) {

    }
}
