package com.wpj.paper.service.plan;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Service("redisPlan")
public class RedisPlanServiceImpl implements PlanService<Object> {

    @Autowired
    RedissonClient redissonClient;

    @Override
    public Object execute(Supplier<Long> supplier, long uid, String isolation) {
        String lockName = String.format("user-lock-%s", uid);
        RLock lock = redissonClient.getSpinLock(lockName);

        log.info("lockName: {}", lockName);

        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                return supplier.get();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }

        throw new RuntimeException("获取锁超时 lockName: " + lockName);
    }

    @Override
    public Object execute(Supplier<Long> supplier, Set<Long> uids, String isolation) {
        String lockNames = JSON.toJSONString(uids.stream().map(uid -> String.format("user-lock:-%s", uid)).collect(Collectors.toSet()));

        RLock[] lockSet = uids.stream()
                .map(uid -> redissonClient.getSpinLock(String.format("user-lock:-%s", uid)))
                .collect(Collectors.toSet()).toArray(new RLock[uids.size()]);

        RLock multiLock = redissonClient.getMultiLock(lockSet);

        log.info("lockNames: {}", lockNames);

        try {
            if (multiLock.tryLock(5, TimeUnit.SECONDS)) {
                return supplier.get();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            multiLock.unlock();
        }

        throw new RuntimeException("获取锁超时 lockNames: " + lockNames);
    }
}
