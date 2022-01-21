package com.product.api.repository;

import com.product.api.dto.OrderDetailDto;
import com.product.api.entity.OrderDetail;
import com.product.api.entity.OrderDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailKey> {

    @Query("select new com.product.api.dto.OrderDetailDto(" +
            "od.orderDetailKey.orderId," +
            "od.orderDetailKey.productId," +
            "od.product.thumbnail," +
            "od.product.name," +
            "od.quantity," +
            "od.unitPrice ," +
            "od.quantity * od.unitPrice)  from OrderDetail od " +
            "inner join Order o " +
            "on od.orderDetailKey.orderId = o.id " +
            "where o.id = :orderId")
    List<OrderDetailDto> findOrderDetailByOrderId(@Param("orderId") int orderId);
}
