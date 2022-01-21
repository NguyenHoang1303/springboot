package com.product.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "privileges")
public class Privilege {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;

    @ManyToMany
    private Set<Role> roles;

    @ManyToMany
    private Set<Account> accounts;

}
