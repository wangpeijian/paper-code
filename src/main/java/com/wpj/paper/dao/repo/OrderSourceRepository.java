package com.wpj.paper.dao.repo;

import com.wpj.paper.dao.entity.OrderSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderSourceRepository extends JpaRepository<OrderSource, Long> {

    @Modifying()
    @Query(value = "insert into order_source(id, num, order_id, price, service_id, status_code, user_id) values(:#{#orderSource.id}, :#{#orderSource.num}, :#{#orderSource.orderId}, :#{#orderSource.price}, :#{#orderSource.serviceId}, :#{#orderSource.statusCode}, :#{#orderSource.userId})", nativeQuery = true)
    int insert(OrderSource orderSource);

    @Modifying()
    @Query(value = "update order_source set status_code = :#{#orderSource.statusCode}  where id = :#{#orderSource.id}", nativeQuery = true)
    int updateStatusCode(OrderSource orderSource);
}
