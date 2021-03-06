package com.wpj.paper.dao.repo.ms;

import com.wpj.paper.dao.entity.OrderSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

@ConditionalOnExpression("'${spring.profiles.active}'=='mssql'")
public interface OrderSourceRepository extends com.wpj.paper.dao.repo.normal.OrderSourceRepository {

    @Modifying()
    @Query(value = "insert into order_source(id, order_id, price, status_code, user_id) values(:#{#orderSource.id}, :#{#orderSource.orderId}, :#{#orderSource.price}, :#{#orderSource.statusCode}, :#{#orderSource.userId})", nativeQuery = true)
    int insert(OrderSource orderSource);

    @Modifying()
    @Query(value = "update order_source set status_code = :#{#orderSource.statusCode}, price = :#{#orderSource.price}  where id = :#{#orderSource.id}", nativeQuery = true)
    int updateStatusCodeAndPrice(OrderSource orderSource);

    @Modifying()
    @Query(value = "delete from order_source", nativeQuery = true)
    int clear();

    @Query(value = "select top 100 * from order_source with (UPDLOCK) where user_id in (:uIds)", nativeQuery = true)
    List<OrderSource> findForUpdate(Set<Long> uIds);
}
