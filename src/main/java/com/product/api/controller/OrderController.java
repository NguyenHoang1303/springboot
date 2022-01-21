package com.product.api.controller;

import com.product.api.entity.Order;
import com.product.api.repository.OrderDetailRepository;
import com.product.api.repository.OrderRepository;
import com.product.api.responseApi.RESTPagination;
import com.product.api.responseApi.RESTResponse;
import com.product.api.service.OrderService;
import com.product.api.specification.FieldOrder;
import com.product.api.specification.HandlerQuery;
import com.product.api.specification.ObjectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public Object testQuery(@RequestParam(name = "name", required = false) String name) {
        System.out.println("name: " + name);
        return orderRepository.findOrderByNameProduct(name, HandlerQuery.creatPagination(1, 15));
    }


    @PreAuthorize("hasAuthority('ORDER_READ')")
    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity getAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "nameProduct", required = false) String nameProduct,
            @RequestParam(name = "id", defaultValue = "0") int id,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "minPrice", defaultValue = "0") int minPrice,
            @RequestParam(name = "maxPrice", defaultValue = "0") int maxPrice,
            @RequestParam(name = "checkOut", defaultValue = "-1") int checkOut,
            @RequestParam(name = "isRemoved", defaultValue = "0") int isRemoved


    ) {
        ObjectFilter filter = ObjectFilter.ObjectFilterBuilder.anObjectFilter()
                .withId(id)
                .withPageSize(pageSize).withPage(page)
                .withMaxPrice(maxPrice).withMinPrice(minPrice)
                .withPhone(phone).withName(name).withEmail(email)
                .withNameProduct(nameProduct)
                .withFrom(from).withTo(to).withCheckOut(checkOut).withIsRemoved(isRemoved)
                .withField(FieldOrder.createdField())
                .build();
        Page paging = orderService.findAll(filter);


        return new ResponseEntity<>(new RESTResponse.Success()
                .setPagination(new RESTPagination(paging.getNumber() + 1, paging.getSize(), paging.getTotalElements()))
                .addData(paging.getContent())
                .buildData(), HttpStatus.OK);

    }

    @PreAuthorize("hasAuthority('ORDER_READ')")
    @GetMapping("/{id}")
    public ResponseEntity findOrderById(@PathVariable Integer id) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(orderService.findById(id))
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ORDER_READ')")
    @GetMapping("/{id}/detail")
    public ResponseEntity findOrderDetailByOrderId(@PathVariable Integer id) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(orderDetailRepository.findOrderDetailByOrderId(id))
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ORDER_WRITE')")
    @PostMapping("/add")
    public ResponseEntity save(@Valid @RequestBody Order order) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(orderService.save(order))
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ORDER_WRITE')")
    @PutMapping("/status/update")
    public ResponseEntity updateStatus(@Valid
                                       @RequestParam int id,
                                       @RequestParam int status) {

        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(orderService.updateStatus(id, status))
                .build(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ORDER_DELETE')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(orderService.delete(id))
                .build(), HttpStatus.OK);
    }


}
