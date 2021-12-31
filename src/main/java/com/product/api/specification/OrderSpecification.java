package com.product.api.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        if (criteria.getOperation().equalsIgnoreCase(">")) {
            return builder.greaterThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());

        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
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
        return null;

    }
}
