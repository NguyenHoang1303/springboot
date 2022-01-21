package com.product.api.specification;

import java.util.HashMap;

public class FieldOrder {

    public static final String ID = "id";
    public static final String PRICE = "totalPrice";
    public static final String NAME = "shipName";
    public static final String PHONE = "shipPhone";
    public static final String EMAIL = "shipEmail";
    public static final String CREATED_AT = "createdAt";
    public static final String CHECK_OUT = "checkOut";

    public static HashMap<String, String> createdField() {
        HashMap<String, String> mapOrder = new HashMap<>();
        mapOrder.put(ID, ID);
        mapOrder.put(ObjectFilter.NAME, NAME);
        mapOrder.put(ObjectFilter.PHONE, PHONE);
        mapOrder.put(ObjectFilter.EMAIL, EMAIL);
        mapOrder.put(ObjectFilter.PRICE, PRICE);
        mapOrder.put(ObjectFilter.IS_REMOVED, ObjectFilter.IS_REMOVED);
        mapOrder.put(CREATED_AT, CREATED_AT);
        mapOrder.put(CHECK_OUT, CHECK_OUT);
        return mapOrder;
    }
}
