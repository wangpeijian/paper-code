package com.wpj.paper.dao.repo;

import com.wpj.paper.dao.entity.BillSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BillSourceRepository extends JpaRepository<BillSource, Long> {

    @Modifying
    @Query(value = "UPDATE bill_source SET status_code = 0", nativeQuery = true)
    int updateAll();
}
