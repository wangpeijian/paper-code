package com.wpj.paper.dao.repo;

import com.wpj.paper.dao.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Modifying()
    @Query(value = "insert into order_item(id, order_id, product_id, num, price, discount) values(:#{#orderItem.id}, :#{#orderItem.orderId},  :#{#orderItem.productId}, :#{#orderItem.num}, :#{#orderItem.price}, :#{#orderItem.discount})", nativeQuery = true)
    int insert(OrderItem orderItem);

    @Modifying()
    @Query(value = "delete from order_item", nativeQuery = true)
    int clear();

    @Query(value = "select * from order_item where product_id in (:pIds)  limit 100 for update", nativeQuery = true)
    List<OrderItem> findForUpdate(Set<Long> pIds);
}
