package com.wpj.paper.dao.repo.normal;

import com.wpj.paper.dao.entity.BillSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

@ConditionalOnExpression("'${spring.profiles.active}'!='mssql'")
public interface BillSourceRepository extends JpaRepository<BillSource, Long> {

    @Modifying()
    @Query(value = "insert  into bill_source(id, user_id, amount, bill_id, status_code) values (:#{#billSource.id}, :#{#billSource.userId}, :#{#billSource.amount}, :#{#billSource.billId}, :#{#billSource.statusCode})", nativeQuery = true)
    int insert(BillSource billSource);

    @Modifying()
    @Query(value = "update bill_source set status_code = :#{#billSource.statusCode}  where id = :#{#billSource.id}", nativeQuery = true)
    int updateStatusCode(BillSource billSource);

    @Modifying()
    @Query(value = "delete from bill_source", nativeQuery = true)
    int clear();

    @Query(value = "select * from bill_source where user_id in (:uIds) limit 100 for update", nativeQuery = true)
    List<BillSource> findForUpdate(Set<Long> uIds);
}
