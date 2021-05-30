package com.wpj.paper.service.plan;

import com.wpj.paper.dao.repo.ProductRepository;
import com.wpj.paper.dao.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Override
    public Object lockUser(long uid, Supplier<Long> supplier) {
        userRepository.forUpdateLock(uid);
        return supplier.get();
    }

    @Override
    public Object lockUser(Set<Long> uids, Supplier<Long> supplier) {
        userRepository.forUpdateLock(uids);
        return supplier.get();
    }

    @Override
    public Object lockProduct(long pid, Supplier<Long> supplier) {
        productRepository.forUpdateLock(pid);
        return supplier.get();
    }

    @Override
    public Object lockProduct(Set<Long> pids, Supplier<Long> supplier) {
        productRepository.forUpdateLock(pids);
        return supplier.get();
    }
}
