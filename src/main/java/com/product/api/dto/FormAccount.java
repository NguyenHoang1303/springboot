package com.product.api.dto;

import com.product.api.entity.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class FormAccount {

    @NotEmpty(message = "username is not empty")
    private String username;

    @NotEmpty(message = "password is not empty")
    private String password;

    @NotNull(message = "username is not null")
    private int roleId;

}
