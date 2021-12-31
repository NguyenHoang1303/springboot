package com.product.api.controllers;

import com.product.api.entites.Category;
import com.product.api.responseApi.HandlerResponse;
import com.product.api.responseApi.RESTPagination;
import com.product.api.responseApi.RESTResponse;
import com.product.api.services.ICategoryService;
import com.product.api.specification.OptionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    ICategoryService categoryServiceImpl;

    @GetMapping()
    public Object getAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "id", defaultValue = "-1") int id,
            @RequestParam(name = "name", required = false) String name
    ){
        OptionFilter filter = OptionFilter.OptionFilterBuilder.anOptionFilter()
                .withPageSize(pageSize)
                .withPage(page)
                .withName(name)
                .withId(id)
                .build();
        Page paging = categoryServiceImpl.findAll(filter);
        return new RESTResponse.Success()
                .setPagination(new RESTPagination(paging.getNumber() + 1, paging.getSize(), paging.getTotalElements()))
                .addData(paging.getContent())
                .buildData();
    }

    @GetMapping("/{id}")
    public HandlerResponse findById(@PathVariable Integer id){
        return HandlerResponse.HandlerResponseBuilder.aHandlerResponse()
                .withStatus(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.name())
                .addData(HandlerResponse.TYPE, HandlerResponse.CATEGORY)
                .addData(HandlerResponse.ITEMS, categoryServiceImpl.findById(id))
                .build();

    }

    @PostMapping("/add")
    public HandlerResponse save(@Valid @RequestBody Category category){
        return HandlerResponse.HandlerResponseBuilder.aHandlerResponse()
                .withStatus(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.name())
                .addData(HandlerResponse.TYPE, HandlerResponse.CATEGORY)
                .addData(HandlerResponse.ITEMS, categoryServiceImpl.save(category))
                .build();
    }

    @PutMapping("/edit")
    public HandlerResponse edit(@Valid @RequestBody Category category){
        return HandlerResponse.HandlerResponseBuilder.aHandlerResponse()
                .withStatus(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.name())
                .addData(HandlerResponse.TYPE, HandlerResponse.CATEGORY)
                .addData(HandlerResponse.ITEMS, categoryServiceImpl.edit(category))
                .build();
    }
}
