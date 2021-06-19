package com.wpj.paper.dao.repo.ms;

import com.wpj.paper.dao.entity.UserInfo;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

@ConditionalOnExpression("'${spring.profiles.active}'=='mssql'")
public interface UserRepository extends com.wpj.paper.dao.repo.normal.UserRepository {

    @Query(value = "select * from user_info with (UPDLOCK) where id = :uid", nativeQuery = true)
    UserInfo forUpdateLock(long uid);

    @Query(value = "select * from user_info with (UPDLOCK) where id in (:uids)", nativeQuery = true)
    List<UserInfo> forUpdateLock(Set<Long> uids);
}
