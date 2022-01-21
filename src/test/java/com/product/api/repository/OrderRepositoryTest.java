package com.product.api.repository;

import com.product.api.entity.Order;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderTest;


    @ParameterizedTest
    @ValueSource(strings = "Bắp")
    void itShouldSelectOrderByNameProduct(String name) {
        List<Order> founds = orderTest.findOrderByNameProduct(name);
        assertThat(founds).isNotNull();

    }

    @ParameterizedTest
    @CsvSource({
            "173,2"
    })
    void updateStatus(int id, int status) {
        orderTest.updateStatus(id, status);
        Order order = orderTest.findOrderById(id);
        assertThat(status).isEqualTo(order.getShipStatus());
    }

    @Test
    @Disabled
    void findOrderById() {
    }

    @Test
    @Disabled
    void removeById() {
    }
}