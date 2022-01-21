package com.product.api.specification;

import com.product.api.constant.Operation;
import com.product.api.entity.OrderDetail;
import com.product.api.entity.Product;
import com.product.api.util.HandlerDate;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.time.LocalDate;

public class HandlerSpecification implements Specification<Order> {

    private final SearchCriteria criteria;


    public HandlerSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        String operation = criteria.getOperation();
        String key = criteria.getKey();
        Object value = criteria.getValue();
        switch (operation) {
            case Operation.GREATER_THAN_OR_EQUAL_TO:
                if (key.equalsIgnoreCase(ObjectFilter.CREATED_AT)) {
                    LocalDate date = HandlerDate.convertStringToLocalDate(value.toString());
                    return builder.greaterThanOrEqualTo(root.get(ObjectFilter.CREATED_AT), date);
                }
                return builder.greaterThanOrEqualTo(root.get(key), value.toString());

            case Operation.lESS_THAN_OR_EQUAL_TO:
                if (key.equalsIgnoreCase(ObjectFilter.CREATED_AT)) {
                    LocalDate date = HandlerDate.convertStringToLocalDate(criteria.getValue().toString());
                    return builder.lessThanOrEqualTo(root.get(ObjectFilter.CREATED_AT), date);
                }
                return builder.lessThanOrEqualTo(root.get(key), value.toString());

            case Operation.EQUAL:
                if (root.get(key).getJavaType() == String.class) {
                    return builder.like(root.get(key), Operation.LIKE + value + Operation.LIKE);
                } else {
                    return builder.equal(root.get(key), value);
                }
            case "join":
//                Join<Order, Account> orderAccountJoin = root.join("account");
                System.out.println("2:" + criteria.getValue());
                Join<Order, OrderDetail> orderDetailJoin = root.join("order_details");
                Join<OrderDetail, Product> orderDetailProductJoin = root.join("order_details").join("products");
                Predicate predicate = builder.or(
//                        builder.like(root.get("id"), "%" + criteria.getValue() + "%"),
                        builder.like(orderDetailProductJoin.get("products.name"), "%" + criteria.getValue() + "%")
//                        builder.like(accountUserJoin.get("phoneNumber"), "%" + criteria.getValue() + "%"),
//                        builder.like(accountUserJoin.get("fullName"), "%" + criteria.getValue() + "%")
                );
                return predicate;
        }

        return null;
    }
}
