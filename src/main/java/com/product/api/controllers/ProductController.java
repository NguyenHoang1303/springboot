package com.product.api.controllers;

import com.product.api.entites.Product;
import com.product.api.repositories.ProductRepository;
import com.product.api.responseApi.HandlerResponse;
import com.product.api.responseApi.RESTPagination;
import com.product.api.responseApi.RESTResponse;
import com.product.api.services.IProductService;
import com.product.api.specification.OptionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    IProductService productService;

    @Autowired
    ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.GET, value = "")
    public Object generictest(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(name = "id", defaultValue = "-1") int id,
            @RequestParam(name = "categoryId", defaultValue = "-1") int categoryId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "optionPrice", defaultValue = "-1") int optionPrice
    ) {
        OptionFilter filter = OptionFilter.OptionFilterBuilder.anOptionFilter()
                .withPageSize(pageSize)
                .withPage(page)
                .withCategoryId(categoryId)
                .withOptionPrice(optionPrice)
                .withName(name)
                .withId(id)
                .build();
        Page paging = productService.findAll(filter);
        return new RESTResponse.Success()
                .setPagination(new RESTPagination(paging.getNumber() + 1, paging.getSize(), paging.getTotalElements()))
                .addData(paging.getContent())
                .buildData();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/detail/{id}")
    public Object findById(@PathVariable Integer id) {
        return new RESTResponse.Success()
                .addData(productService.findById(id))
                .build();
    }

    @PostMapping("/add")
    public HandlerResponse save(@Valid @RequestBody Product product) {
        return HandlerResponse.HandlerResponseBuilder.aHandlerResponse()
                .withStatus(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.name())
                .addData(HandlerResponse.TYPE, HandlerResponse.PRODUCTS)
                .addData(HandlerResponse.ITEMS, productService.save(product))
                .build();
    }

    @PutMapping("/edit")
    public HandlerResponse edit(@Valid @RequestBody Product product) {
        return HandlerResponse.HandlerResponseBuilder.aHandlerResponse()
                .withStatus(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.name())
                .addData(HandlerResponse.TYPE, HandlerResponse.PRODUCTS)
                .addData(HandlerResponse.ITEMS, productService.edit(product))
                .build();

    }

    @DeleteMapping("/delete/{id}")
    public HandlerResponse edit(@PathVariable Integer id) {
        return HandlerResponse.HandlerResponseBuilder.aHandlerResponse()
                .withStatus(HttpStatus.OK.value())
                .withMessage(HttpStatus.OK.name())
                .addData(HandlerResponse.TYPE, HandlerResponse.PRODUCTS)
                .addData(HandlerResponse.ITEMS, productService.delete(id))
                .build();
    }
}
