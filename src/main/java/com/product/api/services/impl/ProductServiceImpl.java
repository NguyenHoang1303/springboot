package com.product.api.services.impl;

import com.product.api.entites.Category;
import com.product.api.entites.Product;
import com.product.api.exceptions.NotFoundException;
import com.product.api.exceptions.SystemException;
import com.product.api.repositories.CategoryRepository;
import com.product.api.repositories.ProductRepository;
import com.product.api.services.IProductService;
import com.product.api.specification.OptionFilter;
import com.product.api.specification.OrderSpecification;
import com.product.api.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductRepository productRepo;

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

        if (filter.getCategoryId() != -1) {
            specification =  specification.and(new OrderSpecification(new SearchCriteria("category_id", ":", filter.getCategoryId())));
        }

        int optionPrice = filter.getOptionPrice();
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setKey("price");
        searchCriteria.setOperation("<<");
        HashMap<String,Integer> map = new HashMap<>();
        if (optionPrice == 1){
            map.put("min",0);
            map.put("max",200000);
            searchCriteria.setValue(map);
            specification = specification.and(new OrderSpecification(searchCriteria));
        }
        if (optionPrice == 2){
            map.put("min",200000);
            map.put("max",400000);
            searchCriteria.setValue(map);
            specification = specification.and(new OrderSpecification(searchCriteria));
        }
        if (optionPrice == 3){
            map.put("min",400000);
            map.put("max",700000);
            searchCriteria.setValue(map);
            specification = specification.and(new OrderSpecification(searchCriteria));
        }

        if (optionPrice == 4){
            map.put("min",700000);
            map.put("max",1000000);
            searchCriteria.setValue(map);
            specification = specification.and(new OrderSpecification(searchCriteria));
        }

        if (optionPrice == 5){
            searchCriteria.setOperation(">");
            searchCriteria.setValue(1000000);
            specification = specification.and(new OrderSpecification(searchCriteria));
        }

        if (filter.getPage() <= 0) {
            filter.setPage(1);
        }
        if (filter.getPageSize() <= 0) {
            filter.setPageSize(10);
        }
        Pageable paging = PageRequest.of(filter.getPage() - 1, filter.getPageSize());

        return productRepo.findAll(specification,paging);
    }

    public Page<Product> pagination(int page, int pageSize){
        return productRepo.findAll(PageRequest.of(page, pageSize));
    }

    public Optional<?> findById(Integer id){
        Optional<?> product = productRepo.findById(id);
        if (!product.isPresent()){
            throw new NotFoundException("Product not found.");
        }
        return product;
    }

    public Product save(Product product){
        Optional<Category> category = categoryRepo.findById(product.getCategory_id());
        if (category.isPresent()){
                java.util.Date date = new java.util.Date();
                java.sql.Date sqlDate= new java.sql.Date(date.getTime());
                product.setCreatedAt(sqlDate);
                product.setStatus(1);
                return productRepo.save(product);
        } else throw new NotFoundException("Category is not found");
    }



    public Product edit(Product newInfoProduct) {
        try {
            Product product = productRepo.getById(newInfoProduct.getId());
            product.setInfo(newInfoProduct);
            return productRepo.save(product);
        }catch (Exception e){
            throw new SystemException("System fail, please try again.");
        }
    }

    @Override
    public Optional<?> delete(Integer id) {
           Optional<?> product = productRepo.findById(id);
           if (product.isPresent()){
               productRepo.deleteById(id);
               return product;
           }else throw new  NotFoundException("Product is not found");
    }


}
