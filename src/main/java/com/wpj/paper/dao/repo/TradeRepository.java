package com.wpj.paper.dao.repo;

import com.wpj.paper.dao.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Query(value = "select id,  debt, source_id from trade where user_id = :userId and debt != 0  order by create_time desc limit 100", nativeQuery = true)
    List<Trade> findDebt(long userId);

    @Modifying
    @Query(value = "update trade set debt = :debt, cash += :cash where id = :id", nativeQuery = true)
    int clearDebt(long id, long cash, long debt);
}
