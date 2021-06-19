package com.wpj.paper.dao.repo.ms;

import com.wpj.paper.dao.entity.OrderItem;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

@ConditionalOnExpression("'${spring.profiles.active}'=='mssql'")
public interface OrderItemRepository extends com.wpj.paper.dao.repo.normal.OrderItemRepository {

    @Modifying()
    @Query(value = "insert into order_item(id, order_id, product_id, num, price, discount) values(:#{#orderItem.id}, :#{#orderItem.orderId},  :#{#orderItem.productId}, :#{#orderItem.num}, :#{#orderItem.price}, :#{#orderItem.discount})", nativeQuery = true)
    int insert(OrderItem orderItem);

    @Modifying()
    @Query(value = "delete from order_item", nativeQuery = true)
    int clear();

    @Query(value = "select top 100 * from order_item with (UPDLOCK) where product_id in (:pIds)", nativeQuery = true)
    List<OrderItem> findForUpdate(Set<Long> pIds);
}
