package com.product.api.dto;

import com.product.api.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RoleDto {

    private Long id;
    private String name;
    private Set<String> privileges = new HashSet<>();

    public RoleDto(Role role) {
        this.id = role.getId();
        this.name = role.getName();
        role.getPrivileges().forEach(privilege -> {
            this.privileges.add(privilege.getName());
        });
    }
}
