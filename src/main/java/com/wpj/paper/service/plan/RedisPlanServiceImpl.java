package com.wpj.paper.service.plan;

import com.alibaba.fastjson.JSON;
import com.wpj.paper.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Service("redisPlan")
public class RedisPlanServiceImpl implements PlanService<Object> {

    @Autowired
    private RedisUtil redisUtil;

    private Object lock(String lockPrefix, long uid, Supplier<Long> supplier) {
        String lockName = String.format("%s-lock-%s", lockPrefix, uid);

        Map.Entry<Boolean, Long> pair = redisUtil.tryLock(lockName, 2000, 10000, supplier);
        if (pair.getKey()) {
            return pair.getValue();
        }

        throw new RuntimeException("获取锁超时 lockName: " + lockName);

    }

    private Object lock(String lockPrefix, Set<Long> ids, Supplier<Long> supplier) {
        TreeSet<Long> tids = new TreeSet<>(ids);

        Set<String> lockSet = tids.stream()
                .map(uid -> String.format("%s-lock:-%s", lockPrefix, uid))
                .collect(Collectors.toSet());

        String lockNames = JSON.toJSONString(lockSet);

        Map.Entry<Boolean, Long> pair = redisUtil.tryLock(lockSet, 2000, 10000, supplier);
        if (pair.getKey()) {
            return pair.getValue();
        }

        throw new RuntimeException("获取锁超时 lockNames: " + lockNames);
    }

    @Override
    public Object lockUser(long uid, Supplier<Long> supplier) {
        return lock("uid", uid, supplier);
    }

    @Override
    public Object lockUser(Set<Long> uids, Supplier<Long> supplier) {
        return lock("uid", uids, supplier);
    }

    @Override
    public Object lockProduct(long pid, Supplier<Long> supplier) {
        return lock("pid", pid, supplier);
    }

    @Override
    public Object lockProduct(Set<Long> pids, Supplier<Long> supplier) {
        return lock("pid", pids, supplier);
    }
}
