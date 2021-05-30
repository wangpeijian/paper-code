package com.wpj.paper.service.biz.pg;

import com.wpj.paper.service.AbstractBizService;
import com.wpj.paper.service.plan.PlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

//@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
//@Service("PgRc")
//public class PgRcServiceImpl extends AbstractBizService {
//    @Override
//    public Object usageBill(long userId, PlanService<?> planService) {
//        return planService.execute(() -> doUsageBill(userId), userId, "PgRc");
//    }
//
//    @Override
//    public Object packageBill(long userId, PlanService<?> planService) {
//        return planService.execute(() -> doPackageBill(userId), userId, "PgRc");
//    }
//
//    @Override
//    public Object recharge(Set<Long> userIds, PlanService<?> planService) {
//        return planService.execute(() -> doRecharge(userIds), userIds, "PgRc");
//    }
//}
