package com.wpj.paper.service.plan;

import java.util.Set;
import java.util.function.Supplier;

public interface PlanService<T> {

    T execute(Supplier<Long> supplier, long uid, String isolation);

    T execute(Supplier<Long> supplier, Set<Long> uid,  String isolation);
}
