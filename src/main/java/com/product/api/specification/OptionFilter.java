package com.product.api.specification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionFilter {

    private int id;
    private int categoryId;
    private String name;
    private String email;
    private String phone;
    private String from;
    private String to;
    private int optionPrice;
    private int page;
    private int pageSize;


    public static final class OptionFilterBuilder {
        private int id;
        private int categoryId;
        private String name;
        private String email;
        private String phone;
        private String from;
        private String to;
        private int optionPrice;
        private int page;
        private int pageSize;

        private OptionFilterBuilder() {
        }

        public static OptionFilterBuilder anOptionFilter() {
            return new OptionFilterBuilder();
        }

        public OptionFilterBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public OptionFilterBuilder withCategoryId(int categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public OptionFilterBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public OptionFilterBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public OptionFilterBuilder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public OptionFilterBuilder withFrom(String from) {
            this.from = from;
            return this;
        }

        public OptionFilterBuilder withTo(String to) {
            this.to = to;
            return this;
        }

        public OptionFilterBuilder withOptionPrice(int optionPrice) {
            this.optionPrice = optionPrice;
            return this;
        }

        public OptionFilterBuilder withPage(int page) {
            this.page = page;
            return this;
        }

        public OptionFilterBuilder withPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public OptionFilter build() {
            OptionFilter optionFilter = new OptionFilter();
            optionFilter.setId(id);
            optionFilter.setCategoryId(categoryId);
            optionFilter.setName(name);
            optionFilter.setEmail(email);
            optionFilter.setPhone(phone);
            optionFilter.setFrom(from);
            optionFilter.setTo(to);
            optionFilter.setOptionPrice(optionPrice);
            optionFilter.setPage(page);
            optionFilter.setPageSize(pageSize);
            return optionFilter;
        }
    }
}
