package com.product.api.enums;

public enum ApplicationPermission {
    PRODUCT_READ("PRODUCT_READ"),
    PRODUCT_WRITE("PRODUCT_WRITE"),
    PRODUCT_DELETE("PRODUCT_DELETE"),
    ORDER_READ("ORDER_READ"),
    ORDER_WRITE("ORDER_WRITE"),
    ORDER_DELETE("ORDER_DELETE");

    private final String permission;

    ApplicationPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
