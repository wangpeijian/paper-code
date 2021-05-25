package com.wpj.paper.service.plan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Supplier;

@Slf4j
@Service("redisPlan")
public class RedisPlanServiceImpl implements PlanService<Object> {

    @Override
    public Object execute(Supplier<Long> supplier, long uid, String isolation) {
        return supplier.get();
    }

    @Override
    public Object execute(Supplier<Long> supplier, Set<Long> uid, String isolation) {
        return null;
    }
}
