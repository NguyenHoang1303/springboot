package com.product.api.services.impl;

import com.product.api.entites.Category;
import com.product.api.entites.Product;
import com.product.api.exceptions.NotFoundException;
import com.product.api.exceptions.SystemException;
import com.product.api.repositories.CategoryRepository;
import com.product.api.repositories.ProductRepository;
import com.product.api.services.IProductService;
import com.product.api.specification.HandlerQuery;
import com.product.api.specification.ObjectFilter;
import com.product.api.specification.HandlerSpecification;
import com.product.api.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductRepository productRepo;

    @Autowired
    CategoryRepository categoryRepo;

    public Page findAll(ObjectFilter filter) {
        return productRepo.findAll(HandlerQuery.creatQuery(filter),
                HandlerQuery.creatPagination(filter.getPage(), filter.getPageSize()));
    }

    public Page<Product> pagination(int page, int pageSize) {
        return productRepo.findAll(PageRequest.of(page, pageSize));
    }

    public Optional<?> findById(Integer id) {
        Optional<?> product = productRepo.findById(id);
        if (!product.isPresent()) {
            throw new NotFoundException("Product not found.");
        }
        return product;
    }

    public Product save(Product product) {
        Optional<Category> category = categoryRepo.findById(product.getCategory_id());
        if (category.isPresent()) {
            product.setCreatedAt(LocalDate.now());
            product.setStatus(1);
            return productRepo.save(product);
        } else throw new NotFoundException("Category is not found");
    }


    public Product edit(Product newInfoProduct) {
        try {
            Product product = productRepo.getById(newInfoProduct.getId());
            product.setInfo(newInfoProduct);
            return productRepo.save(product);
        } catch (Exception e) {
            throw new SystemException("System fail, please try again.");
        }
    }

    @Override
    public Optional<?> delete(Integer id) {
        Optional<?> product = productRepo.findById(id);
        if (product.isPresent()) {
            productRepo.deleteById(id);
            return product;
        } else throw new NotFoundException("Product is not found");
    }


}
