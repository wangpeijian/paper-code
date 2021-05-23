package com.wpj.paper.config;

import com.wpj.paper.dao.repo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class RunnerConfig implements CommandLineRunner {

    @Autowired
    AccountCashRepository accountCashRepository;

    @Autowired
    AccountCreditRepository accountCreditRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BillSourceRepository billSourceRepository;

    @Autowired
    OrderSourceRepository orderSourceRepository;

    @Autowired
    RechargeSourceRepository rechargeSourceRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    ConfigData configData;

    @Transactional
    @Override
    public void run(String... args) {
        log.info("重置账户数据");
        accountCashRepository.updateAll(configData.getCashInit());
        accountCreditRepository.updateAll(configData.getCreditInit());
        productRepository.updateAll(configData.getProductStockMax());

        log.info("清空历史数据");
        billSourceRepository.deleteAll();
        orderSourceRepository.deleteAll();
        rechargeSourceRepository.deleteAll();
        tradeRepository.deleteAll();
    }
}
