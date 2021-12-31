package com.product.api.controllers;

import com.product.api.entites.Order;
import com.product.api.repositories.OrderRepository;
import com.product.api.responseApi.HandlerResponse;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
//        Specification specification = Specification.where(null);
////        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        LocalDate dateTime = LocalDate.parse(from, formatter);
//        //            Date utilDate = format.parse(from);
////            System.out.println(format.format(utilDate));
//        specification =  specification.and(new OrderSpecification(new SearchCriteria("createdAt", ">", "dateTime")));
////
//        return orderRepository.findAll(specification);

        OptionFilter filter = OptionFilter.OptionFilterBuilder.anOptionFilter()
                .withPageSize(pageSize)
                .withPage(page)
                .withOptionPrice(optionPrice)
                .withPhone(phone)
                .withName(name)
                .withId(id)
                .withEmail(email)
                .build();
        Page paging = orderService.findAll(filter);
        return new RESTResponse.Success()
                .setPagination(new RESTPagination(paging.getNumber() + 1, paging.getSize(), paging.getTotalElements()))
                .addData(paging.getContent())
                .buildData();

    }

    @GetMapping("/{id}")
    public Object findById(@PathVariable Integer id) {
        return new RESTResponse.Success()
                .addData(orderService.findById(id))
                .build();
    }

    @PostMapping("/add")
    public HandlerResponse save(@Valid @RequestBody Order order) {
        return HandlerResponse.HandlerResponseBuilder.aHandlerResponse()
                .withStatus(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.name())
                .addData(HandlerResponse.TYPE, HandlerResponse.PRODUCTS)
                .addData(HandlerResponse.ITEMS, orderService.save(order))
                .build();
    }

    @PutMapping("/update_status")
    public HandlerResponse edit(@Valid @RequestBody Integer id, int status) {
        return HandlerResponse.HandlerResponseBuilder.aHandlerResponse()
                .withStatus(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.name())
                .addData(HandlerResponse.TYPE, HandlerResponse.PRODUCTS)
                .addData(HandlerResponse.ITEMS, orderService.updateStatus(id, status))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public HandlerResponse edit(@PathVariable Integer id) {
        return HandlerResponse.HandlerResponseBuilder.aHandlerResponse()
                .withStatus(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.name())
                .addData(HandlerResponse.TYPE, HandlerResponse.PRODUCTS)
                .addData(HandlerResponse.ITEMS, orderService.delete(id))
                .build();
    }

    @GetMapping("/{id}/detail")
    public Object test(@PathVariable Integer id) {
        return new RESTResponse.Success()
                .addData(orderService.findById(id).getOrderDetails())
                .build();
//        return HandlerResponse.HandlerResponseBuilder.aHandlerResponse()
//                .withStatus(HttpStatus.OK.value())
//                .withMessage(HttpStatus.OK.name())
//                .addData(HandlerResponse.TYPE, HandlerResponse.ORDER_DETAIL)
//                .addData(HandlerResponse.ITEMS, orderService.findById(id).getOrderDetails())
//                .build();
    }

}
