package com.product.api.enums;

import lombok.Getter;

@Getter
public enum Status {
    //Product
    LOCK("soldOut", 0),
    ACTIVE("active", 1),

    DELETE("delete", -1);

    private String key;
    private int value;

    Status(String key, int value) {
    }
}
