package com.wpj.paper.dao.repo.ms;

import com.wpj.paper.dao.entity.ReloadLog;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

@ConditionalOnExpression("'${spring.profiles.active}'=='mssql'")
public interface ReloadLogRepository extends com.wpj.paper.dao.repo.normal.ReloadLogRepository {

    @Modifying()
    @Query(value = "insert into reload_log(id, product_id, num) values(:#{#reloadLog.id}, :#{#reloadLog.productId},   :#{#reloadLog.num})", nativeQuery = true)
    int insert(ReloadLog reloadLog);

    @Modifying()
    @Query(value = "delete from reload_log", nativeQuery = true)
    int clear();

    @Query(value = "select top 100 * from reload_log with (UPDLOCK) where product_id in (:pIds)", nativeQuery = true)
    List<?> findForUpdate(Set<Long> pIds);
}
