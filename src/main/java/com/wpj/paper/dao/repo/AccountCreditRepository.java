package com.wpj.paper.dao.repo;

import com.wpj.paper.dao.entity.AccountCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountCreditRepository extends JpaRepository<AccountCredit, Long> {

    @Modifying
    @Query(value = "UPDATE account_credit SET credit_max = :credit, credit = :credit", nativeQuery = true)
    int updateAll(@Param("credit") Long credit);
}
