package com.product.api.repository;

import com.product.api.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

    @Query("SELECT o FROM Order o" +
            " INNER JOIN OrderDetail od" +
            " ON o.id = od.orderDetailKey.orderId" +
            " INNER JOIN Product p" +
            " ON od.orderDetailKey.productId = p.id " +
            " WHERE p.name LIKE %:name%" +
            " GROUP BY o.id")
    Page<Order> findOrderByNameProduct(@Param("name") String name, Pageable pageable);

    @Query("SELECT o FROM Order o" +
            " INNER JOIN OrderDetail od" +
            " ON o.id = od.orderDetailKey.orderId" +
            " INNER JOIN Product p" +
            " ON od.orderDetailKey.productId = p.id " +
            " WHERE p.name LIKE %:name%" +
            " GROUP BY o.id")
    List<Order> findOrderByNameProduct(@Param("name") String name);

    @Modifying
    @Transactional
    @Query("update Order o set o.shipStatus = :status where o.id = :id")
    void updateStatus(@Param("id") int id, @Param("status") int status);

    @Query("select o FROM  Order o where o.id = :id")
    Order findOrderById(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("update Order o set o.isRemoved = 1 where o.id = :id")
    void removeById(@Param("id") int id);


}
