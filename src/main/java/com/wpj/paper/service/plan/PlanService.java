package com.wpj.paper.service.plan;

import java.util.Set;
import java.util.function.Supplier;

public interface PlanService<T> {

    T lockUser(long uid, Supplier<Long> supplier);

    T lockUser(Set<Long> uids, Supplier<Long> supplier);

    T lockProduct(long pid, Supplier<Long> supplier);

    T lockProduct(Set<Long> pids, Supplier<Long> supplier);
}
