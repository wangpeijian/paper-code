package com.wpj.paper.util;

import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@Component
public class RedisUtil {


    @Autowired
    StringRedisTemplate redisTemplate;

    void setKey(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    String getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public <T> Pair<Boolean, T> lockAction(String lockName, long time, Supplier<T> supplier) {
        String identity = UUID.randomUUID().toString();

        // 上锁
        Boolean locked = lock(lockName, identity, time);

        if (locked) {

            try {
                // 执行操作
                return new Pair<>(true, supplier.get());
            } finally {
                // 解锁
                unlock(lockName, identity);
            }

        } else {
            // 上锁失败
            return new Pair<>(false, null);
        }

    }

    Boolean lock(String lockName, String identity, long time) {
        return redisTemplate.opsForValue().setIfAbsent(lockName, identity, time, TimeUnit.SECONDS);
    }

    public Boolean unlock(String lockName, String identity) {
        String script = "if (redis.call('get', KEYS[1]) == ARGV[1]) then return redis.call('del', KEYS[1]) else return 0 end";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(script);
        redisScript.setResultType(Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockName), identity);
        return Long.valueOf(1).equals(result);
    }

    public <T> Pair<Boolean, T> tryLock(String lockName, long timeout, long expireTime, Supplier<T> supplier) {
        return tryLock(Collections.singleton(lockName), timeout, expireTime, supplier);
    }

    public <T> Pair<Boolean, T> tryLock(Set<String> lockNames, long timeout, long expireTime, Supplier<T> supplier) {
        long endTime = System.currentTimeMillis() + timeout;

        Map<String, String> lockIdentity = new HashMap<>();

        try {
            for (String lockName : lockNames) {
                String identity = UUID.randomUUID().toString();
                Boolean locked;
                do {
                    // 超时
                    if (System.currentTimeMillis() + 20 >= endTime) {
                        return new Pair<>(false, null);
                    }

                    locked = lock(lockName, identity, expireTime);

                    if (locked) {
                        lockIdentity.put(lockName, identity);
                    }

                    if (!locked) {
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            log.error("上锁自旋等待失败,", e);
                        }
                    }
                } while (!locked);
            }

            // 执行操作
            return new Pair<>(true, supplier.get());
        } finally {
            // 解锁
            lockIdentity.forEach(this::unlock);
        }

    }
}
