package com.product.api.repository;

import com.product.api.entity.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {


    @Autowired
    private RoleRepository roleTest;

    @ParameterizedTest
    @CsvSource({
            "ROLE_ADMIN, true",
            "SUP_XIT,false"
    })
    void itShouldSelectRoleByName(String nameRole, boolean excepted) {
        //When
        boolean existFound = roleTest.findByName(nameRole) != null;
        //Then
        assertThat(existFound)
                .isEqualTo(excepted);
    }

    @Test
    void itShouldSelectRoleById() {
        //Given
        Role roleNew = new Role();
        roleNew.setId(1L);

        //When
        Role found = roleTest.findRoleById(roleNew.getId());

        //Then
        assertThat(found.getId())
                .isEqualTo(roleNew.getId());
    }
}