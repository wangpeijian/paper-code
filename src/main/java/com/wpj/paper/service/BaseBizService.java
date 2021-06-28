package com.wpj.paper.service;

import com.wpj.paper.service.plan.PlanService;
import org.springframework.beans.factory.BeanNameAware;

import java.util.List;

public interface BaseBizService extends BeanNameAware {

    String getBeanName();

    Object usageBill(long userId, PlanService<?> planService);

    Object packageBill(long userId, PlanService<?> planService);

    List<?> searchOrder();

    List<?> searchStock();

    Object recharge(PlanService<?> planService);

    Object reload(PlanService<?> planService);
}
