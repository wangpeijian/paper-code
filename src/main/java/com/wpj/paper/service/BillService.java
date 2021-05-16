package com.wpj.paper.service;

import com.wpj.paper.dao.entity.BillSource;

public interface BillService {

    BillSource findBillSource(long mockId);

    boolean trade(BillSource billSource);

}
