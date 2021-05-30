package com.wpj.paper.dao.repo;

import com.wpj.paper.dao.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Modifying()
    @Query(value = "insert into order_item(id, order_id, product_id, num, price, discount) values(:#{#orderItem.id}, :#{#orderItem.orderId},  :#{#orderItem.productId}, :#{#orderItem.num}, :#{#orderItem.price}, :#{#orderItem.discount})", nativeQuery = true)
    int insert(OrderItem orderItem);

    @Modifying()
    @Query(value = "delete from order_item", nativeQuery = true)
    int clear();
}
