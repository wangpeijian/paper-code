package com.wpj.paper.service.plan;

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

    @Override
    public Object execute(Supplier<Long> supplier, long uid, String isolation) {
        userRepository.forUpdateLock(uid);
        return supplier.get();
    }

    @Override
    public Object execute(Supplier<Long> supplier, Set<Long> uids, String isolation) {
        userRepository.forUpdateLock(uids);
        return supplier.get();
    }
}
