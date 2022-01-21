package com.product.api.service;

import com.product.api.entity.Order;
import com.product.api.specification.ObjectFilter;
import org.springframework.data.domain.Page;

public interface OrderService {

    Page findAll(ObjectFilter objectFilter);

    Order findById(int id);

    Order save(Order order);

    Order updateStatus(int id, int status);

    Order delete(int id);
}
