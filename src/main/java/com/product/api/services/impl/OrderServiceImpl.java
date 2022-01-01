package com.product.api.services.impl;

import com.product.api.entites.Order;
import com.product.api.exceptions.NotFoundException;
import com.product.api.repositories.OrderRepository;
import com.product.api.services.OrderService;
import com.product.api.specification.HandlerQuery;
import com.product.api.specification.ObjectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Page findAll(ObjectFilter filter) {
        return orderRepository.findAll(HandlerQuery.creatQuery(filter),
                HandlerQuery.creatPagination(filter.getPage(),filter.getPageSize()));
    }

    @Override
    public Order findById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order save(Order order) {

        order.setCreatedAt(LocalDate.now());
        return orderRepository.save(order);
    }

    @Override
    public Order updateStatus(Integer id, int status) {
        Order order = orderRepository.getById(id);
        if (order == null) throw new NotFoundException("Order is not found");
        order.setShipStatus(status);
        order.setUpdatedAt(LocalDate.now());
        return order;
    }

    @Override
    public Optional<?> delete(Integer id) {
        Optional<?> product = orderRepository.findById(id);
        if (product.isPresent()) {
            orderRepository.deleteById(id);
            return product;
        } else throw new NotFoundException("Order is not found");
    }
}
