package com.product.api.controller;

import com.product.api.entity.Category;
import com.product.api.responseApi.RESTPagination;
import com.product.api.responseApi.RESTResponse;
import com.product.api.service.ICategoryService;
import com.product.api.specification.ObjectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    ICategoryService categoryServiceImpl;

    @GetMapping()
    public ResponseEntity getAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "id", defaultValue = "-1") int id,
            @RequestParam(name = "name", required = false) String name
    ) {
        ObjectFilter filter = ObjectFilter.ObjectFilterBuilder.anObjectFilter()
                .withPageSize(pageSize)
                .withPage(page)
                .withName(name)
                .withId(id)
                .build();
        Page paging = categoryServiceImpl.findAll(filter);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setPagination(new RESTPagination(paging.getNumber() + 1, paging.getSize(), paging.getTotalElements()))
                .addData(paging.getContent())
                .buildData(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Integer id) {
        return new ResponseEntity<>(
                new RESTResponse.Success()
                        .addData(categoryServiceImpl.findById(id))
                        .build(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity save(@Valid @RequestBody Category category) {
        return new ResponseEntity<>(
                new RESTResponse.Success()
                        .addData(categoryServiceImpl.save(category))
                        .build(), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity edit(@Valid @RequestBody Category category) {
        return new ResponseEntity<>(
                new RESTResponse.Success()
                        .addData(categoryServiceImpl.edit(category))
                        .build(), HttpStatus.OK);
    }
}
