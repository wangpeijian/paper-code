package com.wpj.paper.dao.repo;

import com.wpj.paper.dao.entity.OrderSource;
import com.wpj.paper.dao.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Query(value = "select id,  debt, source_id from trade where user_id = :userId and debt != 0  order by create_time desc limit 100", nativeQuery = true)
    List<Trade> findDebt(long userId);

    @Modifying
    @Query(value = "update trade set debt = :debt, cash += :cash where id = :id", nativeQuery = true)
    int clearDebt(long id, long cash, long debt);

    @Modifying
    @Query(value = "insert into trade (id, cash, create_time, credit, debt, source_id, type, user_id) values (:#{#trade.id}, :#{#trade.cash}, :#{#trade.createTime}, :#{#trade.credit}, :#{#trade.debt}, :#{#trade.sourceId}, :#{#trade.type}, :#{#trade.userId})", nativeQuery = true)
    int insert(Trade trade);

    @Modifying()
    @Query(value = "delete from trade", nativeQuery = true)
    int clear();

    @Query(value = "select * from trade where user_id in (:uIds) limit 100 for update", nativeQuery = true)
    List<Trade> findForUpdate(Set<Long> uIds);
}
