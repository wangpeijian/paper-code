package com.wpj.paper.controller;

import com.wpj.paper.config.ConfigData;
import com.wpj.paper.dao.entity.AccountCash;
import com.wpj.paper.dao.entity.AccountCredit;
import com.wpj.paper.dao.entity.Product;
import com.wpj.paper.dao.entity.User;
import com.wpj.paper.dao.repo.*;
import com.wpj.paper.service.GeneratorService;
import com.wpj.paper.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
    ConfigData configData;

    @Autowired
    GeneratorService generatorService;

    /**
     * 重置数据
     *
     * @return
     */
    @Transactional
    @GetMapping(value = "/reset")
    public Result<?> reset() {
        log.info("reset accountCashRepository: {}", accountCashRepository.updateAll(configData.getCashInit()));
        log.info("reset accountCreditRepository: {}", accountCreditRepository.updateAll(configData.getCreditInit()));
        log.info("reset productRepository: {}", productRepository.updateAll(configData.getProductStockMax()));

        billSourceRepository.deleteAll();
        orderSourceRepository.deleteAll();
        rechargeSourceRepository.deleteAll();
        tradeRepository.deleteAll();
        orderItemRepository.deleteAll();

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
        ArrayList<User> user = new ArrayList<>(100);
        ArrayList<AccountCash> cash = new ArrayList<>(100);
        ArrayList<AccountCredit> credit = new ArrayList<>(100);

        while (i++ < configData.getUserMax()) {
            String uuid = UUID.randomUUID().toString().replace("-", "");

            user.add(new User(i, uuid.substring(16, 24)));
            cash.add(new AccountCash(i, configData.getCashInit()));
            credit.add(new AccountCredit(i, configData.getCashInit(), configData.getCreditInit()));

            if (user.size() == 100) {
                userRepository.saveAll(user);
                accountCashRepository.saveAll(cash);
                accountCreditRepository.saveAll(credit);

                user.clear();
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
        return Result.ok();
    }

}
