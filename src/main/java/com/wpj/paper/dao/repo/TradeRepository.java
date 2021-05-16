package com.wpj.paper.dao.repo;

import com.wpj.paper.dao.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
