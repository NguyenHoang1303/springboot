package com.product.api.services;

import com.product.api.entites.Order;
import com.product.api.specification.ObjectFilter;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface OrderService {

    Page findAll(ObjectFilter objectFilter);
    Order findById(Integer id);
    Order save(Order order);
    Order updateStatus(Integer id, int status);
    Optional<?> delete(Integer id);
}
