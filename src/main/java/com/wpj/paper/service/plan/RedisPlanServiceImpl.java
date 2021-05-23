package com.wpj.paper.service.plan;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Slf4j
@Service("redisPlan")
public class RedisPlanServiceImpl implements PlanService<Object> {

    @Override
    public Object execute(Supplier<Long> supplier, long uid) {
        return supplier.get();
    }
}