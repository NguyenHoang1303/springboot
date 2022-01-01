package com.product.api.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

public class OrderSpecification implements Specification<Order> {

    private SearchCriteria criteria;

    public OrderSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate
            (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            if (criteria.getKey().equalsIgnoreCase("createdAt")){
               LocalDate date = LocalDate.parse(criteria.getValue().toString(), formatter);
                return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), date);
            }
            return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());

        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            if (criteria.getKey().equalsIgnoreCase("createdAt")){
                LocalDate date = LocalDate.parse(criteria.getValue().toString(), formatter);
                return builder.lessThanOrEqualTo(root.get(criteria.getKey()), date);
            }

            return builder.lessThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());

        }else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(
                        root.get(criteria.getKey()), "%" + criteria.getValue() + "%");

            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }

        } else if (criteria.getOperation().equalsIgnoreCase("<<")) {
            HashMap<String, Integer> map = (HashMap) criteria.getValue();
            return builder.between(root.get(criteria.getKey()), map.get("min"), map.get("max"));
        }


//        else if (criteria.getOperation().equalsIgnoreCase("join")) {
//            Join<Order, Account> orderAccountJoin = root.join("account");
//            Join<Account, User> accountUserJoin = root.join("account").join("user");
//            Predicate predicate = builder.or(
//                    builder.like(root.get("id"), "%" + criteria.getValue() + "%"),
//                    builder.like(orderAccountJoin.get("email"), "%" + criteria.getValue() + "%"),
//                    builder.like(accountUserJoin.get("phoneNumber"), "%" + criteria.getValue() + "%"),
//                    builder.like(accountUserJoin.get("fullName"), "%" + criteria.getValue() + "%")
//            );
//            return predicate;
//        }
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        LocalDate date = LocalDate.parse("2021-12-01 00:00", formatter);
//        return builder.greaterThanOrEqualTo(root.get("createdAt"), date);
        return null;

    }
}
