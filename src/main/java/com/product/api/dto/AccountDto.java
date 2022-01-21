package com.product.api.dto;

import com.product.api.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String username;

    private Set<String> roles;

    private Set<String> privileges;

    public AccountDto(Account account) {
        this.id = account.getId();
        this.username = account.getUsername();
        this.roles = new HashSet<>();
        this.privileges = new HashSet<>();
        if (account.getRoles() != null && account.getRoles().size() > 0) {
            account.getRoles().forEach(role -> {
                this.roles.add(role.getName());
                role.getPrivileges().forEach(privilege -> {
                    this.privileges.add(privilege.getName());
                });
            });
        }

        if (account.getPrivileges() != null && account.getPrivileges().size() > 0) {
            account.getPrivileges().forEach(privilege -> {
                this.privileges.add(privilege.getName());
            });
        }
    }
}
