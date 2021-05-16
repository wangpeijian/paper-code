package com.wpj.paper.dao.repo;

import com.wpj.paper.dao.entity.AccountCash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountCashRepository extends JpaRepository<AccountCash, Long> {

    @Modifying
    @Query(value = "UPDATE account_cash SET cash = :cash", nativeQuery = true)
    int updateAll(@Param("cash") Long cash);

}
