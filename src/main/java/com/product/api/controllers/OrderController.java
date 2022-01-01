package com.product.api.controllers;

import com.product.api.entites.Order;
import com.product.api.repositories.OrderRepository;
import com.product.api.responseApi.RESTPagination;
import com.product.api.responseApi.RESTResponse;
import com.product.api.services.OrderService;
import com.product.api.specification.OptionFilter;
import com.product.api.specification.OrderSpecification;
import com.product.api.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public Object getAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "id", defaultValue = "-1") int id,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "optionPrice",defaultValue = "0") int optionPrice

    ) {
        System.out.println("0:" + from);
        OptionFilter filter = OptionFilter.OptionFilterBuilder.anOptionFilter()
                .withPageSize(pageSize)
                .withPage(page)
                .withOptionPrice(optionPrice)
                .withPhone(phone)
                .withName(name)
                .withFrom(from)
                .withTo(to)
                .withId(id)
                .withEmail(email)
                .build();
        Page paging = orderService.findAll(filter);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setPagination(new RESTPagination(paging.getNumber() + 1, paging.getSize(), paging.getTotalElements()))
                .addData(paging.getContent())
                .buildData(), HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Integer id) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(orderService.findById(id))
                .build(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity save(@Valid @RequestBody Order order) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(orderService.save(order))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/update_status")
    public ResponseEntity edit(@Valid @RequestBody Integer id, int status) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(orderService.updateStatus(id, status))
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity edit(@PathVariable Integer id) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(orderService.delete(id))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity test(@PathVariable Integer id) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(orderService.findById(id).getOrderDetails())
                .build(), HttpStatus.OK);
    }

}
