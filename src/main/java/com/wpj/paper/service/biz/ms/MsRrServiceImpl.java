package com.wpj.paper.service.biz.ms;

import com.wpj.paper.dao.entity.BillSource;
import com.wpj.paper.service.AbstractBizService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
@Service("MsRr")
public class MsRrServiceImpl extends AbstractBizService {
    @Override
    public boolean usageBill(BillSource billSource) {
        return false;
    }

    @Override
    public boolean packageBill() {
        return false;
    }

    @Override
    public boolean recharge() {
        return false;
    }
}
