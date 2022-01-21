package com.product.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    Set<OrderDetail> orderDetails = new HashSet<>();

    public void setOrderDetails(Set<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
        for (OrderDetail ur : orderDetails) {
            ur.setOrder(this);
        }
    }

    @Column(name = "check_out")
    private boolean checkOut;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "ship_name")
    private String shipName;

    @Column(name = "ship_phone")
    private String shipPhone;

    @Column(name = "ship_email")
    private String shipEmail;

    @Column(name = "ship_address")
    private String shipAddress;

    @Column(name = "ship_note")
    private String shipNote;

    @Column(name = "ship_status")
    private int shipStatus;

    @Column(name = "is_removed")
    private int isRemoved;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "deleted_at")
    private LocalDate deletedAt;


}
