package com.wpj.paper.dao.repo.normal;

import com.wpj.paper.dao.entity.ReloadLog;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

@ConditionalOnExpression("'${spring.profiles.active}'!='mssql'")
public interface ReloadLogRepository extends JpaRepository<ReloadLog, Long> {

    @Modifying()
    @Query(value = "insert into reload_log(id, product_id, num) values(:#{#reloadLog.id}, :#{#reloadLog.productId},   :#{#reloadLog.num})", nativeQuery = true)
    int insert(ReloadLog reloadLog);

    @Modifying()
    @Query(value = "delete from reload_log", nativeQuery = true)
    int clear();

    @Query(value = "select * from reload_log where product_id in (:pIds) limit 100 for update", nativeQuery = true)
    List<?> findForUpdate(Set<Long> pIds);
}
