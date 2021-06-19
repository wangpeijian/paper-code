package com.wpj.paper.service.plan;

import com.wpj.paper.dao.repo.ProductRepository;
import com.wpj.paper.dao.repo.UserRepository;
import com.wpj.paper.util.LockPlanRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Service;

import java.util.Set;
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

    @Override
    public Object lockUser(long uid, Supplier<Long> supplier) {

        switch (lockPlanRecord.getCode()) {
            case "mysql-ssi":
                break;
            case "pgsql-ssi":
            case "pgsql-rr":
                break;
            default:
                userRepository.forUpdateLock(uid);
                break;
        }

        return supplier.get();
    }

    @Override
    public Object lockUser(Set<Long> uids, Supplier<Long> supplier) {
        switch (lockPlanRecord.getCode()) {
            case "mysql-ssi":
                break;
            case "pgsql-ssi":
            case "pgsql-rr":
                break;
            default:
                userRepository.forUpdateLock(uids);
                break;
        }

        return supplier.get();
    }

    @Override
    public Object lockProduct(long pid, Supplier<Long> supplier) {
        switch (lockPlanRecord.getCode()) {
            case "mysql-ssi":
                break;
            case "pgsql-ssi":
            case "pgsql-rr":
                break;
            default:
                productRepository.forUpdateLock(pid);
                break;
        }

        return supplier.get();
    }

    @Override
    public Object lockProduct(Set<Long> pids, Supplier<Long> supplier) {
        switch (lockPlanRecord.getCode()) {
            case "mysql-ssi":
                break;
            case "pgsql-ssi":
            case "pgsql-rr":
                break;
            default:
                productRepository.forUpdateLock(pids);
                break;
        }
        return supplier.get();
    }
}
