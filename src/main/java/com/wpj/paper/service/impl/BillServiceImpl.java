package com.wpj.paper.service.impl;

import com.wpj.paper.dao.entity.BillSource;
import com.wpj.paper.dao.repo.BillSourceRepository;
import com.wpj.paper.service.BillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    BillSourceRepository billSourceRepository;

    @Override
    public BillSource findBillSource(long mockId) {
        return billSourceRepository.getOne(mockId);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    @Override
    public boolean trade(BillSource billSource) {
        return false;
    }
}
