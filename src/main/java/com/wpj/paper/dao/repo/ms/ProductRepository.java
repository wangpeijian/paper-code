package com.wpj.paper.dao.repo.ms;

import com.wpj.paper.dao.entity.Product;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

@ConditionalOnExpression("'${spring.profiles.active}'=='mssql'")
public interface ProductRepository extends com.wpj.paper.dao.repo.normal.ProductRepository {

    @Modifying
    @Query(value = "UPDATE product SET stock = :stock", nativeQuery = true)
    int updateAll(long stock);

    @Modifying
    @Query(value = "UPDATE product SET stock = stock - :num where id = :serviceId and stock >= :num", nativeQuery = true)
    int deductStock(Long serviceId, Long num);

    @Query(value = "select top 10 id from product order by stock ASC", nativeQuery = true)
    Set<Long> findInsufficient();

    @Query(value = "select * from product with (UPDLOCK) where id = :pid", nativeQuery = true)
    Product forUpdateLock(long pid);

    @Query(value = "select * from product with (UPDLOCK) where id in (:pids)", nativeQuery = true)
    List<Product> forUpdateLock(Set<Long> pids);

    @Modifying
    @Query(value = "UPDATE product SET stock = stock + :reload where id = :pId", nativeQuery = true)
    int reload(long pId, long reload);
}
