package com.wpj.paper.service.plan;

import com.wpj.paper.config.ConfigData;
import com.wpj.paper.util.ThreadExecutor;
import com.wpj.paper.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Set;
import java.util.function.Supplier;

@Slf4j
@Service("noLockPlan")
public class NoLockPlanServiceImpl implements PlanService<DeferredResult<Result<?>>> {

    private final ConfigData configData;

   private final ThreadExecutor threadExecutor;

    public NoLockPlanServiceImpl(ConfigData configData) {
        this.configData = configData;
        threadExecutor = new ThreadExecutor(this.configData.getPoolSize());
    }

    @Override
    public DeferredResult<Result<?>> execute(Supplier<Long> supplier, long uid, String isolation) {
        DeferredResult<Result<?>> deferredResult = new DeferredResult<>(30000L, () -> Result.error("timeout"));

        threadExecutor.submit(uid, () -> {
            deferredResult.setResult(Result.ok(supplier.get()));
        });

        return deferredResult;
    }

    @Override
    public DeferredResult<Result<?>> execute(Supplier<Long> supplier, Set<Long> uid, String isolation) {
        return null;
    }
}
