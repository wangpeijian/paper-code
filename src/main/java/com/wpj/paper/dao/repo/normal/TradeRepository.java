package com.wpj.paper.dao.repo.normal;

import com.wpj.paper.dao.entity.Trade;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

@ConditionalOnExpression("'${spring.profiles.active}'!='mssql'")
public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Modifying
    @Query(value = "insert into trade (id, cash, create_time, credit, debt, source_id, type, user_id) values (:#{#trade.id}, :#{#trade.cash}, :#{#trade.createTime}, :#{#trade.credit}, :#{#trade.debt}, :#{#trade.sourceId}, :#{#trade.type}, :#{#trade.userId})", nativeQuery = true)
    int insert(Trade trade);

    @Modifying()
    @Query(value = "delete from trade", nativeQuery = true)
    int clear();

    @Query(value = "select * from trade where user_id in (:uIds) limit 100 for update", nativeQuery = true)
    List<Trade> findForUpdate(Set<Long> uIds);
}
