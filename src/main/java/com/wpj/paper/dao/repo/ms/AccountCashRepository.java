package com.wpj.paper.dao.repo.ms;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@ConditionalOnExpression("'${spring.profiles.active}'=='mssql'")
public interface AccountCashRepository extends com.wpj.paper.dao.repo.normal.AccountCashRepository {

    @Modifying
    @Query(value = "UPDATE account_cash SET cash = :cash", nativeQuery = true)
    int updateAll(@Param("cash") Long cash);

}
