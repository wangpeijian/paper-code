package com.wpj.paper.dao.repo;

import com.wpj.paper.dao.entity.OrderSource;
import com.wpj.paper.dao.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface OrderSourceRepository extends JpaRepository<OrderSource, Long> {

    @Modifying()
    @Query(value = "insert into order_source(id, order_id, price, status_code, user_id) values(:#{#orderSource.id}, :#{#orderSource.orderId}, :#{#orderSource.price}, :#{#orderSource.statusCode}, :#{#orderSource.userId})", nativeQuery = true)
    int insert(OrderSource orderSource);

    @Modifying()
    @Query(value = "update order_source set status_code = :#{#orderSource.statusCode}, price = :#{#orderSource.price}  where id = :#{#orderSource.id}", nativeQuery = true)
    int updateStatusCodeAndPrice(OrderSource orderSource);

    @Modifying()
    @Query(value = "delete from order_source", nativeQuery = true)
    int clear();

    @Query(value = "select * from order_source where user_id in (:uIds) limit 100 for update", nativeQuery = true)
    List<OrderSource> findForUpdate(Set<Long> uIds);
}
