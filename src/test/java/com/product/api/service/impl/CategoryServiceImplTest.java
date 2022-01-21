package com.product.api.service.impl;

import com.product.api.entity.Category;
import com.product.api.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepoTest;

    @Captor
    private ArgumentCaptor<Category> customerArgumentCaptor;

    @InjectMocks
    private CategoryServiceImpl categoryService;


    @Test
    void itShouldSaveNewCategory() {
        Category category = new Category();
        category.setName("Trứng");
        category.setThumbnail("Trứng.png");
        category.setDescription("Hình tròn, có thể màu trắng hoặc vàng, kích thước to nhỏ khác nhau");

        categoryService.save(category);

        customerArgumentCaptor = ArgumentCaptor.forClass(Category.class);

        //then
        then(categoryRepoTest).should().save(customerArgumentCaptor.capture());
        Category valueCate = customerArgumentCaptor.getValue();
        assertThat(valueCate).isEqualTo(category);
    }

    @Test
    void itShouldEditCategory() {

        Category category = new Category();
        category.setId(100);
        category.setName("Trứng123");
        category.setThumbnail("Trứng.png");
        category.setDescription("Hình tròn, có thể màu trắng hoặc vàng, kích thước to nhỏ khác nhau");
        Optional<Category> existCate = categoryRepoTest.findById(category.getId());

        given(existCate.isPresent()).willReturn(false);

        assertThatThrownBy(() -> categoryService.edit(category))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Id Number " + category.getId() + " is not valid");

        then(categoryRepoTest).shouldHaveNoInteractions();


    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }


}