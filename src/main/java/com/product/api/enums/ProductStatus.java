package com.product.api.enums;

public enum ProductStatus {
    SOLD_OUT("soldOut", 0),
    ON_SALE("onSale", 1);

    private final String key;
    private final Integer value;

    ProductStatus(String key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }
}
