package com.wpj.paper.service.plan;

//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Set;
//import java.util.TreeSet;
//import java.util.concurrent.TimeUnit;
//import java.util.function.Supplier;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service("redisPlan")
//public class RedisPlanServiceImplBak implements PlanService<Object> {
//
//    @Autowired
//    RedissonClient redissonClient;
//
//    private Object lock(String lockPrefix, long uid, Supplier<Long> supplier) {
//        String lockName = String.format("%s-lock-%s", lockPrefix, uid);
//        RLock lock = redissonClient.getSpinLock(lockName);
//
//
//        try {
//            if (lock.tryLock(2, TimeUnit.SECONDS)) {
//                return supplier.get();
//            }
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } finally {
//            try{
//                if(lock.isLocked()){
//                    lock.unlock();
//                }
//            }catch (Exception e){
//                log.error("singleLock unlock error");
//            }
//        }
//
//        throw new RuntimeException("获取锁超时 lockName: " + lockName);
//    }
//
//    private Object lock(String lockPrefix, Set<Long> ids, Supplier<Long> supplier) {
//        TreeSet<Long> tids = new TreeSet<>(ids);
//
//        String lockNames = JSON.toJSONString(tids.stream().map(uid -> String.format("%s-lock:-%s", lockPrefix, uid)).collect(Collectors.toSet()));
//
//        RLock[] lockSet = tids.stream()
//                .map(uid -> redissonClient.getSpinLock(String.format("%s-lock:-%s", lockPrefix, uid)))
//                .collect(Collectors.toSet()).toArray(new RLock[tids.size()]);
//
//        RLock multiLock = redissonClient.getMultiLock(lockSet);
//
//
//        try {
//            if (multiLock.tryLock(2, TimeUnit.SECONDS)) {
//                return supplier.get();
//            }
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } finally {
//            try{
//                multiLock.unlock();
//            }catch (Exception e){
//                log.error("multiLock unlock error");
//            }
//        }
//
//        throw new RuntimeException("获取锁超时 lockNames: " + lockNames);
//    }
//
//    @Override
//    public Object lockUser(long uid, Supplier<Long> supplier) {
//        return lock("uid", uid, supplier);
//    }
//
//    @Override
//    public Object lockUser(Set<Long> uids, Supplier<Long> supplier) {
//        return lock("uid", uids, supplier);
//    }
//
//    @Override
//    public Object lockProduct(long pid, Supplier<Long> supplier) {
//        return lock("pid", pid, supplier);
//    }
//
//    @Override
//    public Object lockProduct(Set<Long> pids, Supplier<Long> supplier) {
//        return lock("pid", pids, supplier);
//    }
//}
