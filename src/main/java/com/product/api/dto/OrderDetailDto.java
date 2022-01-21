package com.product.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderDetailDto {
    private Integer orderId;
    private Integer productId;
    private String thumbnail;
    private String nameProduct;
    private int quantity;
    private int unitPrice;
    private int totalPrice;


    public OrderDetailDto(Integer orderId, Integer productId, String thumbnail, String nameProduct, int quantity, int unitPrice, int totalPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.thumbnail = thumbnail;
        this.nameProduct = nameProduct;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }
}
