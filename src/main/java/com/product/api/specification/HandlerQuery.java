package com.product.api.specification;

import com.product.api.constant.Operation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashMap;

public class HandlerQuery {

    public static Specification creatQuery(ObjectFilter filter) {

        HashMap<String, String> mapField = filter.getMapField();
        Specification specification = Specification.where(null);
        System.out.println("0.name: ");
        if (filter.getName() != null && filter.getName().length() > 0) {
            System.out.println("1.name: " + filter.getName());
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(ObjectFilter.NAME),
                    Operation.EQUAL,
                    filter.getName())));
        }

//        if (filter.getNameProduct() != null && filter.getNameProduct().length() > 0) {
//            System.out.println("2:" + filter.getNameProduct());
//            specification = specification.and(new HandlerSpecification(new SearchCriteria("name", "join", filter.getNameProduct())));
//        }

        if (filter.getEmail() != null && filter.getEmail().length() > 0) {
            System.out.println("email: " + filter.getEmail());
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(ObjectFilter.EMAIL),
                    Operation.EQUAL,
                    filter.getEmail())));
        }

        if (filter.getPhone() != null && filter.getPhone().length() > 0) {
            System.out.println("phone: " + filter.getPhone());
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(ObjectFilter.PHONE),
                    Operation.EQUAL,
                    filter.getPhone())));
        }

        if (filter.getId() != -1) {
            System.out.println("id: " + filter.getId());
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(ObjectFilter.ID),
                    Operation.EQUAL,
                    filter.getId())));
        }

        if (filter.getFrom() != null && filter.getFrom().length() > 0) {
            System.out.println("from: " + filter.getFrom());
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(ObjectFilter.CREATED_AT),
                    Operation.GREATER_THAN_OR_EQUAL_TO,
                    filter.getFrom())));
        }

        if (filter.getTo() != null && filter.getTo().length() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(ObjectFilter.CREATED_AT),
                    Operation.lESS_THAN_OR_EQUAL_TO,
                    filter.getTo())));
        }

        if (filter.getMinPrice() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(ObjectFilter.PRICE),
                    Operation.GREATER_THAN_OR_EQUAL_TO,
                    filter.getMinPrice())));
        }

        if (filter.getMaxPrice() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(ObjectFilter.PRICE),
                    Operation.lESS_THAN_OR_EQUAL_TO,
                    filter.getMaxPrice())));
        }

        if (filter.getCheckOut() != -1) {
            System.out.println("checkOut: " + filter.getCheckOut());
            specification = specification.and(new HandlerSpecification((new SearchCriteria(
                    mapField.get(FieldOrder.CHECK_OUT),
                    Operation.EQUAL,
                    filter.getCheckOut()
            ))));
        }

        return specification;
    }

    public static Pageable creatPagination(int page, int pageSize) {
        return PageRequest.of(page - 1, pageSize, Sort.Direction.DESC, ObjectFilter.CREATED_AT);
    }
}
