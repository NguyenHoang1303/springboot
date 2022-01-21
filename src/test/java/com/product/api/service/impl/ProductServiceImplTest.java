package com.product.api.service.impl;

import com.product.api.entity.Product;
import com.product.api.repository.CategoryRepository;
import com.product.api.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {

    @MockBean
    ProductRepository productTest;

    @MockBean
    CategoryRepository categoryTest;

    @InjectMocks
    ProductServiceImpl productServiceTest;

    @Test
    void findAll() {
    }

    @Test
    void pagination() {
    }

    @Test
    void findById() {
    }

    @Test
    void save() {
        Product product = new Product();
        product.setName("Cơm chiên trứng");
        product.setPrice(35000);
        product.setCategory_id(1);
        product.setThumbnail("avatar.png");
        product.setDescription("cơm chiên với trứng");
        product.setDetail("cơm chiên với trứng ung");


    }

    @Test
    void edit() {
    }

    @Test
    void delete() {
    }
}