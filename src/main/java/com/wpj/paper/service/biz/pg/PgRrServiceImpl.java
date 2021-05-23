package com.wpj.paper.service.biz.pg;

import com.wpj.paper.dao.entity.BillSource;
import com.wpj.paper.dao.entity.OrderSource;
import com.wpj.paper.dao.entity.RechargeSource;
import com.wpj.paper.service.AbstractBizService;
import com.wpj.paper.service.plan.PlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
@Service("PgRr")
public class PgRrServiceImpl extends AbstractBizService {
   @Override
    public Object usageBill(BillSource billSource, PlanService<?> planService) {
        return planService.execute(() -> doUsageBill(billSource), billSource.getUserId());
    }

    @Override
    public Object packageBill(OrderSource orderSource, PlanService<?> planService) {
        return planService.execute(() -> doPackageBill(orderSource), orderSource.getUserId());
    }

    @Override
    public Object recharge(RechargeSource rechargeSource, PlanService<?> planService) {
        return planService.execute(() -> doRecharge(rechargeSource), rechargeSource.getUserId());
    }
}
