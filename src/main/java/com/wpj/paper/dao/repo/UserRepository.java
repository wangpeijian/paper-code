package com.wpj.paper.dao.repo;

import com.wpj.paper.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Query(value = "insert into `user` values (:id, :name)", nativeQuery = true)
    int add(@Param("id") Long id, @Param("name") String name);

    @Query(value = "select * from `user` where id = :uid for update", nativeQuery = true)
    User forUpdateLock(long uid);

    @Query(value = "select * from `user` where id in (:uids) for update", nativeQuery = true)
    List<User> forUpdateLock(Set<Long> uids);
}
