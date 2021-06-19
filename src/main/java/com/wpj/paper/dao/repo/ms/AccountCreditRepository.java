package com.wpj.paper.dao.repo.ms;

import com.wpj.paper.dao.entity.AccountCredit;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@ConditionalOnExpression("'${spring.profiles.active}'=='mssql'")
public interface AccountCreditRepository extends com.wpj.paper.dao.repo.normal.AccountCreditRepository {

    @Modifying
    @Query(value = "UPDATE account_credit SET credit_max = :credit, credit = :credit", nativeQuery = true)
    int updateAll(@Param("credit") Long credit);
}
