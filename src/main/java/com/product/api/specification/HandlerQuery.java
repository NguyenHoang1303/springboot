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
        if (filter.getName() != null && filter.getName().length() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(FieldFilter.NAME),
                    Operation.EQUAL,
                    filter.getName())));
        }

//        if (filter.getNameProduct() != null && filter.getNameProduct().length() > 0) {
//            System.out.println("1:"+ filter.getNameProduct());
//            specification = specification.and(new HandlerSpecification(new SearchCriteria("name", "join", filter.getNameProduct())));
//        }

        if (filter.getEmail() != null && filter.getEmail().length() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(FieldFilter.EMAIL),
                    Operation.EQUAL,
                    filter.getEmail())));
        }

        if (filter.getPhone() != null && filter.getPhone().length() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(FieldFilter.PHONE),
                    Operation.EQUAL,
                    filter.getPhone())));
        }

        if (filter.getId() != -1) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(FieldFilter.ID),
                    Operation.EQUAL,
                    filter.getId())));
        }

        if (filter.getFrom() != null && filter.getFrom().length() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(FieldFilter.CREATED_AT),
                    Operation.GREATER_THAN_OR_EQUAL_TO,
                    filter.getFrom())));
        }

        if (filter.getFrom() != null && filter.getFrom().length() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(FieldFilter.CREATED_AT),
                    Operation.lESS_THAN_OR_EQUAL_TO,
                    filter.getTo())));
        }

        if (filter.getMinPrice() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(FieldFilter.PRICE),
                    Operation.GREATER_THAN_OR_EQUAL_TO,
                    filter.getMinPrice())));
        }

        if (filter.getMaxPrice() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(FieldFilter.PRICE),
                    Operation.lESS_THAN_OR_EQUAL_TO,
                    filter.getMaxPrice())));
        }

        return specification;
    }

    public static Pageable creatPagination(int page, int pageSize){
        return PageRequest.of(page - 1, pageSize, Sort.Direction.DESC, "createdAt");
    }
}
