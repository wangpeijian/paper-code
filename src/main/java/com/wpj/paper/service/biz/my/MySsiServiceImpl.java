package com.wpj.paper.service.biz.my;

import com.wpj.paper.service.AbstractBizService;
import com.wpj.paper.service.plan.PlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
@Service("MySsi")
public class MySsiServiceImpl extends AbstractBizService {
   @Override
    public Object usageBill(long userId, PlanService<?> planService) {
         return planService.execute(() -> doUsageBill(userId), userId, "MySsi");
    }

    @Override
    public Object packageBill(long userId, PlanService<?> planService) {
        return planService.execute(() -> doPackageBill(userId), userId, "MySsi");
    }

    @Override
    public Object recharge(Set<Long> userIds, PlanService<?> planService) {
        return planService.execute(() -> doRecharge(userIds), userIds, "MySsi");
    }
}
