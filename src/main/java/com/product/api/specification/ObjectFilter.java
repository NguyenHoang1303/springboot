package com.product.api.specification;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class ObjectFilter {

    private int id;
    private int categoryId;
    private int minPrice;
    private int maxPrice;
    private int page;
    private int pageSize;
    private String name;
    private String nameProduct;
    private String email;
    private String phone;
    private String from;
    private String to;
    private HashMap<String,String> mapField;

    public static final class ObjectFilterBuilder {
        private int id;
        private int categoryId;
        private int minPrice;
        private int maxPrice;
        private int page;
        private int pageSize;
        private String name;
        private String nameProduct;
        private String email;
        private String phone;
        private String from;
        private String to;
        private HashMap<String,String> mapField;

        private ObjectFilterBuilder() {
        }

        public static ObjectFilterBuilder anObjectFilter() {
            return new ObjectFilterBuilder();
        }

        public ObjectFilterBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public ObjectFilterBuilder withCategoryId(int categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public ObjectFilterBuilder withMinPrice(int minPrice) {
            this.minPrice = minPrice;
            return this;
        }

        public ObjectFilterBuilder withMaxPrice(int maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }

        public ObjectFilterBuilder withPage(int page) {
            this.page = page;
            return this;
        }

        public ObjectFilterBuilder withPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public ObjectFilterBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ObjectFilterBuilder withNameProduct(String nameProduct) {
            this.nameProduct = nameProduct;
            return this;
        }

        public ObjectFilterBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public ObjectFilterBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public ObjectFilterBuilder withFrom(String from) {
            this.from = from;
            return this;
        }

        public ObjectFilterBuilder withTo(String to) {
            this.to = to;
            return this;
        }
        public ObjectFilterBuilder withField(HashMap<String,String> mapField){
            this.mapField = mapField;
            return this;
        }

        public ObjectFilter build() {
            ObjectFilter objectFilter = new ObjectFilter();
            objectFilter.setId(id);
            objectFilter.setCategoryId(categoryId);
            objectFilter.setMinPrice(minPrice);
            objectFilter.setMaxPrice(maxPrice);
            objectFilter.setPage(page);
            objectFilter.setPageSize(pageSize);
            objectFilter.setName(name);
            objectFilter.setNameProduct(nameProduct);
            objectFilter.setEmail(email);
            objectFilter.setPhone(phone);
            objectFilter.setFrom(from);
            objectFilter.setTo(to);
            objectFilter.setMapField(mapField);
            return objectFilter;
        }
    }
}
