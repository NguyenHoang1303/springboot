package com.product.api.specification;

import com.product.api.constant.Operation;
import com.product.api.exception.RequestValidException;
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
                    mapField.get(ObjectFilter.NAME),
                    Operation.EQUAL,
                    filter.getName())));
        }

        if (filter.getNameProduct() != null && filter.getNameProduct().length() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria("name", "join", filter.getNameProduct())));
        }

        if (filter.getEmail() != null && filter.getEmail().length() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(ObjectFilter.EMAIL),
                    Operation.EQUAL,
                    filter.getEmail())));
        }

        if (filter.getPhone() != null && filter.getPhone().length() > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(ObjectFilter.PHONE),
                    Operation.EQUAL,
                    filter.getPhone())));
        }

        int id = filter.getId();
        if (id < 0) {
            throw new RequestValidException("Order is not found! Please check the information again.");
        } else if (id > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(ObjectFilter.ID),
                    Operation.EQUAL,
                    filter.getId())));
        }

        if (filter.getFrom() != null && filter.getFrom().length() > 0) {
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

        int minPrice = filter.getMinPrice();
        if (minPrice < 0) {
            throw new RequestValidException("Order is not found! Please check the information again.");
        } else if (minPrice > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(ObjectFilter.PRICE),
                    Operation.GREATER_THAN_OR_EQUAL_TO, filter.getMinPrice())));
        }

        int maxPrice = filter.getMaxPrice();
        if (maxPrice < 0) {
            throw new RequestValidException("Order is not found! Please check the information again.");
        } else if (maxPrice > 0) {
            specification = specification.and(new HandlerSpecification(new SearchCriteria(
                    mapField.get(ObjectFilter.PRICE),
                    Operation.lESS_THAN_OR_EQUAL_TO, filter.getMaxPrice())));
        }

        int checkOut = filter.getCheckOut();
        if (checkOut != 0 && checkOut != 1 && checkOut != -1) {
            throw new RequestValidException("Order is not found! Please check the information again.");
        } else if (checkOut == -1) {
            specification = specification.and(null);
        } else {
            specification = specification.and(new HandlerSpecification((new SearchCriteria(
                    mapField.get(FieldOrder.CHECK_OUT),
                    Operation.EQUAL, filter.getCheckOut()))));
        }

        if (filter.getIsRemoved() != 1 && filter.getIsRemoved() != 0) {
            throw new RequestValidException("Order is not found! Please check the information again.");
        } else {
            specification = specification.and(new HandlerSpecification((new SearchCriteria(
                    mapField.get(ObjectFilter.IS_REMOVED),
                    Operation.EQUAL, filter.getIsRemoved()))));
        }

        return specification;
    }

    public static Pageable creatPagination(int page, int pageSize) {
        return PageRequest.of(page - 1, pageSize, Sort.Direction.DESC, ObjectFilter.CREATED_AT);
    }
}
