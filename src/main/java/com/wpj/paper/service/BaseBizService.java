package com.wpj.paper.service;

import com.wpj.paper.service.plan.PlanService;
import org.springframework.beans.factory.BeanNameAware;

import java.util.List;
import java.util.Set;

public interface BaseBizService extends BeanNameAware {

    String getBeanName();

    Object usageBill(long userId, PlanService<?> planService);

    Object packageBill(long userId, PlanService<?> planService);

    Object recharge(Set<Long> userIds, PlanService<?> planService);

    List<?> searchOrder();

    List<?> searchStock();

    Object reload(Set<Long> ids, PlanService<?> planService);
}
