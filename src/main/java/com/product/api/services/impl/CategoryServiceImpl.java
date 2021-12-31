package com.product.api.services.impl;

import com.product.api.entites.Category;
import com.product.api.exceptions.NotFoundException;
import com.product.api.repositories.CategoryRepository;
import com.product.api.services.ICategoryService;
import com.product.api.specification.OptionFilter;
import com.product.api.specification.OrderSpecification;
import com.product.api.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    CategoryRepository categoryRepo;

    public Page findAll(OptionFilter filter){
        Specification specification = Specification.where(null);

        if (filter.getName() != null && filter.getName().length() > 0) {
            specification =  specification.and(new OrderSpecification(new SearchCriteria("name", ":", filter.getName())));
        }


        if (filter.getId() != -1) {
            specification =  specification.and(new OrderSpecification(new SearchCriteria("id", ":", filter.getId())));
        }
        if (filter.getPage() <= 0) {
            filter.setPage(1);
        }
        if (filter.getPageSize() <= 0) {
            filter.setPageSize(10);
        }
        Pageable paging = PageRequest.of(filter.getPage() - 1, filter.getPageSize());
        return categoryRepo.findAll(specification,paging);
    }

    public Optional<?> findById(Integer id){
        Optional<?> category = categoryRepo.findById(id);
        if (!category.isPresent()){
            throw new NotFoundException("Category not found.");
        }
        return category;
    }

    public Category save(Category category){
        Date date = new Date();
        java.sql.Date sqlDate= new java.sql.Date(date.getTime());
        category.setCreatedAt(sqlDate);
        return categoryRepo.save(category);
    }

    public Category edit(Category newInfoCategory) {
        try {
            Category category = categoryRepo.getById(newInfoCategory.getId());
            category.setInfo(newInfoCategory);
            return categoryRepo.save(category);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }




}
