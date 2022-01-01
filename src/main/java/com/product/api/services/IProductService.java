package com.product.api.services;

import com.product.api.entites.Product;
import com.product.api.specification.ObjectFilter;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IProductService {

    Page findAll(ObjectFilter filter);
    Optional<?> findById(Integer id);
    Product save(Product product);
    Product edit(Product newInfoCategory);
    Optional<?> delete(Integer id);

    Page<Product> pagination(int page, int pageSize);
}
