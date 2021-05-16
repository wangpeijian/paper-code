package com.wpj.paper.dao.repo;

import com.wpj.paper.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user WHERE name = :name", nativeQuery = true)
    List<User> findByName(@Param("name") String name);

    @Modifying
    @Query(value = "insert into user values (:id, :name)", nativeQuery = true)
    int add(@Param("id") Long id, @Param("name") String name);
}
