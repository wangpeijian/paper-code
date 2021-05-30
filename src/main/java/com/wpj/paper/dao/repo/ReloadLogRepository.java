package com.wpj.paper.dao.repo;

import com.wpj.paper.dao.entity.ReloadLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReloadLogRepository extends JpaRepository<ReloadLog, Long> {

    @Modifying()
    @Query(value = "insert into reload_log(id, product_id, num) values(:#{#reloadLog.id}, :#{#reloadLog.productId},   :#{#reloadLog.num})", nativeQuery = true)
    int insert(ReloadLog reloadLog);

    @Modifying()
    @Query(value = "delete from reload_log", nativeQuery = true)
    int clear();
}
