package com.wpj.paper.service;

import com.wpj.paper.dao.entity.BillSource;

public interface BaseBizService {

    boolean usageBill(BillSource billSource);

    boolean packageBill();

    boolean recharge();
}
