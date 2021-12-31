package com.product.api.services;

import com.product.api.entites.Category;
import com.product.api.specification.OptionFilter;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ICategoryService {

    Page findAll(OptionFilter filter);
    Optional<?> findById(Integer id);
    Category save(Category category);
    Category edit(Category newInfoCategory);
}
