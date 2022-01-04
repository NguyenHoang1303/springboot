package com.product.api.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Product name is required")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Price is required")
    private Integer price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Set<OrderDetail> orderDetails = new HashSet<>();


    public void setUserRoles(Set<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
        for(OrderDetail ur : orderDetails) {
            ur.setProduct(this);
        }
    }

    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    @JsonIgnore
    private Category category;

    @NotNull(message = "Category is required")
    @Column(name = "category_id")
    private Integer category_id;

    @NotEmpty(message = "Description is required")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotEmpty(message = "Detail is required")
    @Column(columnDefinition = "TEXT")
    private String detail;

    @NotEmpty(message = "Thumbnail is required")
    @Column(columnDefinition = "TEXT")
    private String thumbnail;


    private Integer status;


    @Column(name = "created_at")
    private LocalDate createdAt;


    @Column(name = "updated_at")
    private LocalDate updatedAt;


    @Column(name = "deleted_at")
    private LocalDate deletedAt;

    public void setInfo(Product newInfo ){

        this.name = newInfo.getName();
        this.price = newInfo.getPrice();
        this.description = newInfo.getDescription();
        this.thumbnail = newInfo.getThumbnail();
        this.detail = newInfo.getDetail();
        this.category_id = newInfo.getCategory_id();
        this.updatedAt = LocalDate.now();
    }
}
