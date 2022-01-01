package com.product.api.controllers;

import com.product.api.entites.Product;
import com.product.api.repositories.ProductRepository;
import com.product.api.responseApi.RESTPagination;
import com.product.api.responseApi.RESTResponse;
import com.product.api.services.IProductService;
import com.product.api.specification.FieldFilter;
import com.product.api.specification.ObjectFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    IProductService productService;

    @Autowired
    ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity getRecord(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "id", defaultValue = "-1") int id,
            @RequestParam(name = "categoryId", defaultValue = "-1") int categoryId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "minPrice",defaultValue = "-1") int minPrice,
            @RequestParam(name = "maxPrice",defaultValue = "-1") int maxPrice
    ) {
        ObjectFilter filter = ObjectFilter.ObjectFilterBuilder.anObjectFilter()
                .withId(id)
                .withPageSize(pageSize).withPage(page)
                .withCategoryId(categoryId)
                .withMaxPrice(maxPrice).withMinPrice(minPrice)
                .withName(name)
                .withField(new FieldFilter.Product().createdField().build())
                .build();
        Page paging = productService.findAll(filter);
        return new ResponseEntity<>(new RESTResponse.Success()
                .setPagination(new RESTPagination(paging.getNumber() + 1, paging.getSize(), paging.getTotalElements()))
                .addData(paging.getContent())
                .buildData(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/detail/{id}")
    public ResponseEntity findById(@PathVariable Integer id) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(productService.findById(id))
                .build(),HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity save(@Valid @RequestBody Product product) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(productService.save(product))
                .build(), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity edit(@Valid @RequestBody Product product) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(productService.edit(product))
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity edit(@PathVariable Integer id) {
        return new ResponseEntity<>(new RESTResponse.Success()
                .addData(productService.delete(id))
                .build(), HttpStatus.OK);

    }
}
