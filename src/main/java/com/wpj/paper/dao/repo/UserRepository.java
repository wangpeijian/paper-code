package com.wpj.paper.dao.repo;

import com.wpj.paper.dao.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserInfo, Long> {

    @Modifying
    @Query(value = "insert into user_info values (:id, :name)", nativeQuery = true)
    int add(@Param("id") Long id, @Param("name") String name);

    @Query(value = "select * from user_info where id = :uid for update", nativeQuery = true)
    UserInfo forUpdateLock(long uid);

    @Query(value = "select * from user_info where id in (:uids) for update", nativeQuery = true)
    List<UserInfo> forUpdateLock(Set<Long> uids);
}
