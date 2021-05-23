package com.wpj.paper.service;

import com.wpj.paper.dao.entity.BillSource;
import com.wpj.paper.dao.entity.OrderSource;
import com.wpj.paper.dao.entity.Product;
import com.wpj.paper.dao.entity.RechargeSource;
import com.wpj.paper.service.plan.PlanService;

import java.util.List;

public interface BaseBizService {

    Object usageBill(BillSource billSource, PlanService<?> planService);

    Object packageBill(OrderSource orderSource, PlanService<?> planService);

    Object recharge(RechargeSource rechargeSource, PlanService<?> planService);

    List<OrderSource> searchOrder();

    List<Product> searchStock();
}
