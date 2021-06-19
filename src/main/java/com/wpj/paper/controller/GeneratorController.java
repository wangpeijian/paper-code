package com.wpj.paper.controller;

import com.wpj.paper.config.ConfigData;
import com.wpj.paper.config.RunnerConfig;
import com.wpj.paper.dao.entity.AccountCash;
import com.wpj.paper.dao.entity.AccountCredit;
import com.wpj.paper.dao.entity.UserInfo;
import com.wpj.paper.dao.repo.normal.*;
import com.wpj.paper.service.GeneratorService;
import com.wpj.paper.util.ZipfGenerator;
import com.wpj.paper.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping(value = "/gen")
public class GeneratorController {

    @Autowired
    UserRepository userRepository;

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
    OrderItemRepository orderItemRepository;

    @Autowired
    ReloadLogRepository reloadLogRepository;

    @Autowired
    ConfigData configData;

    @Autowired
    GeneratorService generatorService;

    @Autowired
    RunnerConfig runnerConfig;

    @Autowired
    ZipfGenerator productZipf;

    /**
     * 重置数据
     *
     * @return
     */
    @Transactional
    @GetMapping(value = "/reset")
    public Result<?> reset() {

        runnerConfig.run();

        return Result.ok();
    }

    /**
     * 创建用户相关数据
     *
     * @return
     */
    @Transactional
    @GetMapping(value = "/user")
    public Result<?> user() {
        accountCashRepository.deleteAll();
        accountCreditRepository.deleteAll();
        userRepository.deleteAll();

        long i = 0;
        ArrayList<UserInfo> userInfo = new ArrayList<>(100);
        ArrayList<AccountCash> cash = new ArrayList<>(100);
        ArrayList<AccountCredit> credit = new ArrayList<>(100);

        while (i++ < configData.getUserMax()) {
            String uuid = UUID.randomUUID().toString().replace("-", "");

            userInfo.add(new UserInfo(i, uuid.substring(16, 24)));
            cash.add(new AccountCash(i, configData.getCashInit()));
            credit.add(new AccountCredit(i, configData.getCashInit(), configData.getCreditInit()));

            if (userInfo.size() == 100) {
                userRepository.saveAll(userInfo);
                accountCashRepository.saveAll(cash);
                accountCreditRepository.saveAll(credit);

                userInfo.clear();
                cash.clear();
                credit.clear();
            }
        }

        return Result.ok();
    }

    /**
     * 创建商品相关数据
     *
     * @return
     */
    @GetMapping(value = "/product")
    public Result<?> product() throws InterruptedException {
//        productRepository.deleteAll();

//        long i = 477300;
//        ArrayList<Product> products = new ArrayList<>(100);
//
//        while (i++ < configData.getProductMax()) {
//            String uuid = UUID.randomUUID().toString().replace("-", "");
//
//            products.add(new Product(i, uuid.substring(16, 24), configData.getProductStockMax()));
//            if (products.size() == 100) {
//                generatorService.saveProducts(products);
//
//                products.clear();
//
//                log.info("save index: {}%", ((double)i) / 10000);
//
//                Thread.sleep(100);
//            }
//        }
//
        Thread.sleep(2000);
        return Result.ok();
    }

    /**
     * 创建商品相关数据
     *
     * @return
     */
    @GetMapping(value = "/updateZipf/{zipf}")
    public Result<?> updateZipf(@PathVariable("zipf") float zipf) {
        productZipf.update((int) configData.getProductMax(), zipf);
        return Result.ok();
    }
}
