package com.wpj.paper.controller;

import com.wpj.paper.dao.entity.*;
import com.wpj.paper.dao.repo.*;
import com.wpj.paper.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.UUID;

@Transactional
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

    final private long userMax = 300;
    final private long cashInit = 10000L;
    final private long creditInit = 20000L;

    final private long productMax = 100;
    final private long productStockMax = 500;

    final private long billMax = 10 * 10000;
    final private long billPriceMax = 500;

    /**
     * 创建用户相关数据
     *
     * @return
     */
    @GetMapping(value = "/reset")
    public Result<?> reset() {
        log.info("reset accountCashRepository: {}", accountCashRepository.updateAll(cashInit));
        log.info("reset accountCreditRepository: {}", accountCreditRepository.updateAll(creditInit));
        log.info("reset productRepository: {}", productRepository.updateAll(creditInit));
        return Result.ok();
    }

    /**
     * 创建用户相关数据
     *
     * @return
     */
    @GetMapping(value = "/user")
    public Result<?> user() {
        userRepository.deleteAll();
        accountCashRepository.deleteAll();
        accountCreditRepository.deleteAll();

        long i = 0;
        ArrayList<User> user = new ArrayList<>(100);
        ArrayList<AccountCash> cash = new ArrayList<>(100);
        ArrayList<AccountCredit> credit = new ArrayList<>(100);

        while (i++ < userMax) {
            String uuid = UUID.randomUUID().toString().replace("-", "");

            user.add(new User(i, uuid.substring(16, 24)));
            cash.add(new AccountCash(i, cashInit));
            credit.add(new AccountCredit(i, creditInit, creditInit));

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
    public Result<?> product() {
        productRepository.deleteAll();

        int i = 0;
        ArrayList<Product> products = new ArrayList<>(100);

        while (i++ < productMax) {
            String uuid = UUID.randomUUID().toString().replace("-", "");

            products.add(new Product((long) i, uuid.substring(16, 24), productStockMax));
            if (products.size() == 100) {
                productRepository.saveAll(products);
                products.clear();
            }
        }

        return Result.ok();
    }

    /**
     * @return
     */
    @GetMapping(value = "/bill")
    public Result<?> bill() {
        billSourceRepository.deleteAll();

        int i = 0;
        ArrayList<BillSource> bills = new ArrayList<>(100);

        while (i++ < billMax) {
            String uuid = UUID.randomUUID().toString().replace("-", "");

//            bills.add(new BillSource((long) i, uuid.substring(16, 24), productStockMax));
            if (bills.size() == 100) {
                billSourceRepository.saveAll(bills);
                bills.clear();
            }
        }

        return Result.ok();
    }

}
