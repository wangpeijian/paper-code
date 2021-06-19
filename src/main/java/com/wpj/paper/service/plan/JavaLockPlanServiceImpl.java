package com.wpj.paper.service.plan;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

@Slf4j
@Service("javaLockPlan")
public class JavaLockPlanServiceImpl implements PlanService<Object> {

    private final static String USER_LOCK = "user";
    private final static String PRODUCT_LOCK = "product";
    Map<Long, Lock> userLocks = new ConcurrentHashMap<>();
    Map<Long, Lock> productLocks = new ConcurrentHashMap<>();

    private Map<Long, Lock> getLockMap(String type) {
        switch (type) {
            case USER_LOCK:
                return userLocks;

            case PRODUCT_LOCK:
                return productLocks;
        }

        return null;
    }

    public Object lock(String type, long id, Supplier<Long> supplier) {
        Map<Long, Lock> lockMap = getLockMap(type);

        Lock lock = lockMap.computeIfAbsent(id, (Long key) -> new ReentrantLock());
        List<Lock> locks = new ArrayList<>();
        try {
            if (lock.tryLock(2, TimeUnit.SECONDS)) {
                locks.add(lock);
                return supplier.get();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            locks.forEach(lockItem -> {
                try {
                    lockItem.unlock();
                } catch (Exception ignored) {

                }
            });
        }

        throw new RuntimeException(String.format("获取锁超时 type: %s , id: %s", type, id));
    }

    public Object lock(String type, Set<Long> ids, Supplier<Long> supplier) {
        Map<Long, Lock> lockMap = getLockMap(type);

        TreeSet<Long> tids = new TreeSet<>(ids);

        List<Lock> locks = new ArrayList<>();
        try {
            for (Long tid : tids) {
                Lock lock = lockMap.computeIfAbsent(tid, (Long key) -> new ReentrantLock());

                if (lock.tryLock(2, TimeUnit.SECONDS)) {
                    locks.add(lock);
                } else {
                    throw new RuntimeException(String.format("获取锁超时 type: %s , id: %s", type, tid));
                }
            }

            if (locks.size() == tids.size()) {
                return supplier.get();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {

            locks.forEach(lock -> {
                try {
                    lock.unlock();
                } catch (Exception ignored) {

                }
            });
        }

        throw new RuntimeException(String.format("获取锁超时 type: %s , ids: %s", type, JSON.toJSONString(ids)));
    }

    @Override
    public Object lockUser(long uid, Supplier<Long> supplier) {
        return lock(USER_LOCK, uid, supplier);
    }

    @Override
    public Object lockUser(Set<Long> uids, Supplier<Long> supplier) {
        return lock(USER_LOCK, uids, supplier);
    }

    @Override
    public Object lockProduct(long pid, Supplier<Long> supplier) {
        return lock(PRODUCT_LOCK, pid, supplier);
    }

    @Override
    public Object lockProduct(Set<Long> pids, Supplier<Long> supplier) {
        return lock(PRODUCT_LOCK, pids, supplier);
    }
}
