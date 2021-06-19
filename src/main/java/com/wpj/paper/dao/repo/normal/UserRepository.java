package com.wpj.paper.dao.repo.normal;

import com.wpj.paper.dao.entity.UserInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

@ConditionalOnExpression("'${spring.profiles.active}'!='mssql'")
public interface UserRepository extends JpaRepository<UserInfo, Long> {

    @Query(value = "select * from user_info where id = :uid for update", nativeQuery = true)
    UserInfo forUpdateLock(long uid);

    @Query(value = "select * from user_info where id in (:uids) for update", nativeQuery = true)
    List<UserInfo> forUpdateLock(Set<Long> uids);
}
