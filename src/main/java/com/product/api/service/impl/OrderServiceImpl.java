package com.product.api.service.impl;

import com.product.api.entity.Order;
import com.product.api.exception.NotFoundException;
import com.product.api.exception.RequestValidException;
import com.product.api.repository.OrderRepository;
import com.product.api.service.OrderService;
import com.product.api.specification.HandlerQuery;
import com.product.api.specification.ObjectFilter;
import com.product.api.util.HandlerDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Page findAll(ObjectFilter filter) {
        return orderRepository.findAll(HandlerQuery.creatQuery(filter),
                HandlerQuery.creatPagination(filter.getPage(), filter.getPageSize()));
    }

    @Override
    public Order findById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order save(Order order) {
        order.setCreatedAt(HandlerDate.getCurrentTimeDetail());
        return orderRepository.save(order);
    }

    @Override
    public Order updateStatus(int id, int status) {
        Order orderExist = orderRepository.findOrderById(id);
        if (orderExist == null) throw new NotFoundException("Order is not found");
        int statusOrder = orderExist.getShipStatus();
        if (statusOrder == 2 || statusOrder == 3) {
            orderExist.setShipStatus(status);
        } else if (statusOrder == 1 && status == 0) {
            orderExist.setShipStatus(status);
        } else throw new RequestValidException("Update status fail! please check status update.");
        orderExist.setUpdatedAt(HandlerDate.getCurrentTimeDetail());
        return orderRepository.save(orderExist);
    }

    @Override
    public Order delete(int id) {
        Order order = orderRepository.findOrderById(id);
        if (order == null) throw new NotFoundException("Order is not found");
        order.setIsRemoved(1);
        order.setDeletedAt(HandlerDate.getCurrentTimeDetail());
        return orderRepository.save(order);
    }
}
