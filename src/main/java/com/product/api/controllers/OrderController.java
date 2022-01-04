package com.product.api.controllers;

import com.product.api.entites.Order;
import com.product.api.repositories.OrderRepository;
import com.product.api.responseApi.RESTPagination;
import com.product.api.responseApi.RESTResponse;
import com.product.api.services.OrderService;
import com.product.api.specification.FieldOrder;
import com.product.api.specification.FieldProduct;
import com.product.api.specification.ObjectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(method = RequestMethod.GET, value = "")
    public Object getAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "nameProduct", required = false) String nameProduct,
            @RequestParam(name = "id", defaultValue = "-1") int id,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "minPrice",defaultValue = "-1") int minPrice,
            @RequestParam(name = "maxPrice",defaultValue = "-1") int maxPrice,
            @RequestParam(name = "checkOut", defaultValue = "-1") int checkOut


    ) {
        ObjectFilter filter = ObjectFilter.ObjectFilterBuilder.anObjectFilter()
                .withId(id)
                .withPageSize(pageSize).withPage(page)
                .withMaxPrice(maxPrice).withMinPrice(minPrice)
                .withPhone(phone).withName(name).withEmail(email)
                .withNameProduct(nameProduct)
                .withFrom(from).withTo(to).withCheckOut(checkOut)
                .withField(FieldOrder.createdField())
                .build();
        System.out.println("fieldOrder: " + FieldOrder.createdField());
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
