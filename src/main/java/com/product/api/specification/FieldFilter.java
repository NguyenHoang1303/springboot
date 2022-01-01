package com.product.api.specification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldFilter {
    //Base field
    public static final String FROM = "from";
    public static final String TO = "to";
    public static final String ID = "id";
    public static final String MAX_PRICE = "maxPrice";
    public static final String MIN_PRICE = "minPrice";
    public static final String PRICE = "price";
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String CREATED_AT = "createdAt";
    private HashMap<String, String> mapField;

    public static class Order extends FieldFilter {
        public static final String NAME = "shipName";
        public static final String PHONE = "shipPhone";
        public static final String EMAIL = "shipEmail";
        public static final String PRICE = "totalPrice";
        private final HashMap<String, String> mapOrder;

        public Order() {
            this.mapOrder = new HashMap<>();
        }

        public FieldFilter.Order createdField() {
            this.mapOrder.put(ID,ID);
            this.mapOrder.put(FieldFilter.NAME,NAME);
            this.mapOrder.put(FieldFilter.PHONE,PHONE);
            this.mapOrder.put(FieldFilter.EMAIL,EMAIL);
            this.mapOrder.put(FieldFilter.PRICE,PRICE);
            this.mapOrder.put(CREATED_AT,CREATED_AT);

            return this;
        }

        public HashMap<String, String> build() {
            FieldFilter restResponse = new FieldFilter();
            restResponse.setMapField(this.mapOrder);
            return restResponse.getMapField();
        }
    }

    public static class Product{
        public static final String CATEGORY_ID = "category_id";
        private final HashMap<String, String> mapOrder;
        public Product(){
            this.mapOrder = new HashMap<>();
        }
        public FieldFilter.Product createdField() {
            this.mapOrder.put(NAME,NAME);
            this.mapOrder.put(ID,ID);
            this.mapOrder.put(PRICE,PRICE);
            this.mapOrder.put(CATEGORY_ID,CATEGORY_ID);
            return this;
        }
        public HashMap<String, String> build() {
            FieldFilter restResponse = new FieldFilter();
            restResponse.setMapField(this.mapOrder);
            return restResponse.getMapField();
        }
    }

}
