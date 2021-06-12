package com.wpj.paper.service.plan;

import com.wpj.paper.dao.repo.ProductRepository;
import com.wpj.paper.dao.repo.UserRepository;
import com.wpj.paper.util.LockPlanRecord;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

@Slf4j
@Service("dbPlan")
public class DBPlanServiceImpl implements PlanService<Object> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    LockPlanRecord lockPlanRecord;

    public <T> Pair<Boolean, T> tryLock(long timeout, Supplier<T> supplier) {
        long endTime = System.currentTimeMillis() + timeout;
        boolean locked = false;
        do {
            // 超时
            if (System.currentTimeMillis() + 20 >= endTime) {
                throw new Error("上锁自旋超时");
            }

            try {
                supplier.get();
                locked = true;
            } catch (Exception lockAcquisitionException) {
                log.error("pgsql上锁失败,", lockAcquisitionException);
            }

            if(!locked){
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    log.error("上锁自旋等待失败,", e);
                }
            }

        } while (!locked);

        // 执行操作
        return new Pair<>(true, supplier.get());
    }

    @Override
    public Object lockUser(long uid, Supplier<Long> supplier) {

        switch (lockPlanRecord.getCode()){
            case "mysql-ssi":
                break;
            case "pgsql-rr":
//                tryLock(100, () -> userRepository.forUpdateLock(uid));
                break;
            default:
                userRepository.forUpdateLock(uid);
                break;
        }

        return supplier.get();
    }

    @Override
    public Object lockUser(Set<Long> uids, Supplier<Long> supplier) {
        switch (lockPlanRecord.getCode()){
            case "mysql-ssi":
                break;
            case "pgsql-rr":
//                tryLock(100, () -> userRepository.forUpdateLock(uids));
                break;
            default:
                userRepository.forUpdateLock(uids);
                break;
        }

        return supplier.get();
    }

    @Override
    public Object lockProduct(long pid, Supplier<Long> supplier) {
        switch (lockPlanRecord.getCode()){
            case "mysql-ssi":
                break;
            case "pgsql-rr":
//                tryLock(100, () -> productRepository.forUpdateLock(pid));
                break;
            default:
                productRepository.forUpdateLock(pid);
                break;
        }

        return supplier.get();
    }

    @Override
    public Object lockProduct(Set<Long> pids, Supplier<Long> supplier) {
        switch (lockPlanRecord.getCode()){
            case "mysql-ssi":
                break;
            case "pgsql-rr":
//                tryLock(100, () -> productRepository.forUpdateLock(pids));
                break;
            default:
                productRepository.forUpdateLock(pids);
                break;
        }
        return supplier.get();
    }
}
