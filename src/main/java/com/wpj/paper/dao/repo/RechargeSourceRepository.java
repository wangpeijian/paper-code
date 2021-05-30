package com.wpj.paper.dao.repo;

import com.wpj.paper.dao.entity.BillSource;
import com.wpj.paper.dao.entity.RechargeSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RechargeSourceRepository extends JpaRepository<RechargeSource, Long> {
    @Modifying()
    @Query(value = "insert into recharge_source(id, user_id, amount, recharge_id, status_code) values (:#{#rechargeSource.id}, :#{#rechargeSource.userId}, :#{#rechargeSource.amount}, :#{#rechargeSource.rechargeId}, :#{#rechargeSource.statusCode})", nativeQuery = true)
    int insert(RechargeSource rechargeSource);

    @Modifying()
    @Query(value = "update recharge_source set status_code = :#{#rechargeSource.statusCode}  where id = :#{#rechargeSource.id}", nativeQuery = true)
    int updateStatusCode(RechargeSource rechargeSource);

    @Modifying()
    @Query(value = "delete from recharge_source", nativeQuery = true)
    int clear();
}
