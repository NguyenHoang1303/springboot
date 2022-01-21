package com.product.api.specification;

import java.util.HashMap;

public class FieldProduct {

    public static final String CATEGORY_ID = "category_id";

    public static HashMap<String, String> createdField() {
        HashMap<String, String> mapOrder = new HashMap<>();
        mapOrder.put(ObjectFilter.NAME, ObjectFilter.NAME);
        mapOrder.put(ObjectFilter.ID, ObjectFilter.ID);
        mapOrder.put(ObjectFilter.PRICE, ObjectFilter.PRICE);
        mapOrder.put(ObjectFilter.IS_REMOVED, ObjectFilter.IS_REMOVED);
        mapOrder.put(CATEGORY_ID, CATEGORY_ID);
        return mapOrder;
    }
}
