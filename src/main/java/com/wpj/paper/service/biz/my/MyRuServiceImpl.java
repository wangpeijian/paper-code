package com.wpj.paper.service.biz.my;

import com.wpj.paper.dao.entity.BillSource;
import com.wpj.paper.service.AbstractBizService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_UNCOMMITTED)
@Service("MyRu")
public class MyRuServiceImpl extends AbstractBizService {
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
