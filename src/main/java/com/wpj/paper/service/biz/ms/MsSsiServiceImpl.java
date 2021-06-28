package com.wpj.paper.service.biz.ms;

import com.wpj.paper.service.AbstractBizService;
import com.wpj.paper.service.plan.PlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
@Service("MsSsi")
public class MsSsiServiceImpl extends AbstractBizService {
    @Override
    public Object usageBill(long userId, PlanService<?> planService) {
        return doUsageBill(userId, planService);
    }

    @Override
    public Object packageBill(long userId, PlanService<?> planService) {
        return doPackageBill(userId, planService);
    }

    @Override
    public Object recharge(PlanService<?> planService) {
        return doRecharge(planService);
    }

    @Override
    public List<?> searchOrder() {
        return doSearchOrder();
    }

    @Override
    public List<?> searchStock() {
        return doSearchStock();
    }

    @Override
    public Object reload(PlanService<?> planService) {
        return doReload(planService);
    }
}
