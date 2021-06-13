package com.wpj.paper.service.biz.pg;

import com.wpj.paper.service.AbstractBizService;
import com.wpj.paper.service.plan.PlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
@Service("PgRc")
public class PgRcServiceImpl extends AbstractBizService {
    @Override
    public Object usageBill(long userId, PlanService<?> planService) {
        return doUsageBill(userId, planService);
    }

    @Override
    public Object packageBill(long userId, PlanService<?> planService) {
        return doPackageBill(userId, planService);
    }

    @Override
    public Object recharge(Set<Long> userIds, PlanService<?> planService) {
        return doRecharge(userIds, planService);
    }
@Override
    public List<?> searchOrder() {
        return doSearchOrder();
    }

    @Override
    public List<?> searchStock() {
        return doSearchStock();
    }@Override
    public Object reload(Set<Long> pIds, PlanService<?> planService) {
        return doReload(pIds, planService);
    }
}
