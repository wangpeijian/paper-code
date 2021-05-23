package com.wpj.paper.service.plan;

import java.util.function.Supplier;

public interface PlanService<T> {

    T execute(Supplier<Long> supplier, long uid);

}
