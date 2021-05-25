package com.wpj.paper.dao.repo;

import com.wpj.paper.dao.entity.BillSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BillSourceRepository extends JpaRepository<BillSource, Long> {

    @Modifying()
    @Query(value = "insert  into bill_source(id, user_id, amount, bill_id, status_code) values (:#{#billSource.id}, :#{#billSource.userId}, :#{#billSource.amount}, :#{#billSource.billId}, :#{#billSource.statusCode})", nativeQuery = true)
    int insert(BillSource billSource);

    @Modifying()
    @Query(value = "update bill_source set status_code = :#{#billSource.statusCode}  where id = :#{#billSource.id}", nativeQuery = true)
    int  updateStatusCode(BillSource billSource);
}
