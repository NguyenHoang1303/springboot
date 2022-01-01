package com.product.api.services.impl;

import com.product.api.entites.Order;
import com.product.api.exceptions.NotFoundException;
import com.product.api.repositories.OrderRepository;
import com.product.api.services.OrderService;
import com.product.api.specification.OptionFilter;
import com.product.api.specification.OrderSpecification;
import com.product.api.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Page findAll(OptionFilter filter) {

        Specification specification = Specification.where(null);
        if (filter.getName() != null && filter.getName().length() > 0) {
            specification = specification.and(new OrderSpecification(new SearchCriteria("shipName", ":", filter.getName())));
        }

        if (filter.getEmail() != null && filter.getEmail().length() > 0) {
            specification = specification.and(new OrderSpecification(new SearchCriteria("shipEmail", ":", filter.getEmail())));
        }

        if (filter.getPhone() != null && filter.getPhone().length() > 0) {
            specification = specification.and(new OrderSpecification(new SearchCriteria("shipPhone", ":", filter.getPhone())));
        }

        if (filter.getId() != -1) {
            specification = specification.and(new OrderSpecification(new SearchCriteria("id", ":", filter.getId())));
        }



        if (filter.getFrom() != null && filter.getFrom().length() > 0) {
            specification = specification.and(new OrderSpecification(new SearchCriteria("createdAt", ">", filter.getFrom())));
        }

        if (filter.getFrom() != null && filter.getFrom().length() > 0) {
            specification = specification.and(new OrderSpecification(new SearchCriteria("createdAt", "<", filter.getTo())));
        }





        int optionPrice = filter.getOptionPrice();
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setKey("totalPrice");
        searchCriteria.setOperation("<<");
        HashMap<String, Integer> map = new HashMap<>();
        if (optionPrice == 1) {
            map.put("min", 0);
            map.put("max", 200000);
            searchCriteria.setValue(map);
            specification = specification.and(new OrderSpecification(searchCriteria));
        }
        if (optionPrice == 2) {
            map.put("min", 200000);
            map.put("max", 400000);
            searchCriteria.setValue(map);
            specification = specification.and(new OrderSpecification(searchCriteria));
        }
        if (optionPrice == 3) {
            map.put("min", 400000);
            map.put("max", 700000);
            searchCriteria.setValue(map);
            specification = specification.and(new OrderSpecification(searchCriteria));
        }

        if (optionPrice == 4) {
            map.put("min", 700000);
            map.put("max", 1000000);
            searchCriteria.setValue(map);
            specification = specification.and(new OrderSpecification(searchCriteria));
        }

        if (optionPrice == 5) {
            searchCriteria.setOperation(">");
            searchCriteria.setValue(1000000);
            specification = specification.and(new OrderSpecification(searchCriteria));
        }




        if (filter.getPage() <= 0) {
            filter.setPage(1);
        }
        if (filter.getPageSize() <= 0) {
            filter.setPageSize(10);
        }
        Pageable paging = PageRequest.of(filter.getPage() - 1, filter.getPageSize());
        return orderRepository.findAll(specification, paging);
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
