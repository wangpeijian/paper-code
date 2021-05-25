package com.wpj.paper.service;

import com.wpj.paper.config.ConfigData;
import com.wpj.paper.dao.entity.*;
import com.wpj.paper.dao.repo.*;
import com.wpj.paper.service.model.ConsumeResult;
import com.wpj.paper.service.model.RechargeResult;
import com.wpj.paper.util.Disperser;
import com.wpj.paper.util.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public abstract class AbstractBizService implements BaseBizService {

    @Autowired
    BillSourceRepository billSourceRepository;

    @Autowired
    OrderSourceRepository orderSourceRepository;

    @Autowired
    RechargeSourceRepository rechargeSourceRepository;

    @Autowired
    AccountCashRepository accountCashRepository;

    @Autowired
    AccountCreditRepository accountCreditRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Snowflake snowflake;

    @Autowired
    ConfigData configData;

    public BillSource createBillSource(long userId) {
        long id = Long.parseLong(snowflake.nextId());
        long price = new Random().nextInt((int) configData.getCashInit() / 10);

        // 随机生成一条记录
        BillSource billSource = new BillSource(id, userId, price, id);
        billSourceRepository.insert(billSource);
        return billSource;
    }

    public RechargeSource createRechargeSource(long userId) {
        long id = Long.parseLong(snowflake.nextId());
        long price = new Random().nextInt((int) configData.getCashInit() / 10);

        // 随机生成一条记录
        RechargeSource rechargeSource = new RechargeSource(id, userId, price, id);
        rechargeSourceRepository.insert(rechargeSource);
        return rechargeSource;
    }

    public OrderSource createOrderSource(long userId) {
        long id = Long.parseLong(snowflake.nextId());
        long price = new Random().nextInt((int) configData.getCashInit() / 10);
        long serviceId = Disperser.get(configData.getProductMax());
        long num = new Random().nextInt((int) configData.getProductStockMax() / 100);

        // 随机生成一条记录
        OrderSource source = new OrderSource(id, userId, id, serviceId, price, num);
        orderSourceRepository.insert(source);
        return source;
    }

    /**
     * 支付包费订单
     *
     * @return
     */
    public long doPackageBill(long userId) {
        OrderSource orderSource = createOrderSource(userId);

        long errorCode = 1;

        AccountCash accountCash = accountCashRepository.getOne(userId);
        AccountCredit accountCredit = accountCreditRepository.getOne(userId);

        // 扣减账单
        ConsumeResult consumeResult = consume(accountCash.getCash(), accountCredit.getCredit(), orderSource.getPrice());

        // 余额不足直接返回
        if (consumeResult.hasDebt()) {
            errorCode = -1;
        }

        // 余额足够,尝试扣减库存
        int deductStockSuccess = productRepository.deductStock(orderSource.getServiceId(), orderSource.getNum());
        if (deductStockSuccess != 1) {
            errorCode = -2;
        }

        // 创建流水记录
        Trade trade = createTrade(1, orderSource.getOrderId(), userId, consumeResult);
        tradeRepository.insert(trade);

        // 更新账户金额
        accountCashRepository.save(new AccountCash(userId, consumeResult.getCash()));
        accountCreditRepository.save(new AccountCredit(userId, accountCredit.getCredit(), accountCredit.getCreditMax()));

        //更新order source
        orderSource.setStatusCode(errorCode);
        orderSourceRepository.updateStatusCode(orderSource);

        return errorCode;
    }

    /**
     * 支付按量付费账单
     *
     * @param userId
     * @return
     */
    public long doUsageBill(long userId) {
        BillSource billSource = createBillSource(userId);

        AccountCash accountCash = accountCashRepository.getOne(billSource.getUserId());
        AccountCredit accountCredit = accountCreditRepository.getOne(billSource.getUserId());

        // 扣减账单
        ConsumeResult consumeResult = consume(accountCash.getCash(), accountCredit.getCredit(), billSource.getAmount());

        // 创建流水记录
        Trade trade = createTrade(2, billSource.getBillId(), billSource.getUserId(), consumeResult);
        tradeRepository.insert(trade);

        // 更新账户金额
        accountCashRepository.save(new AccountCash(billSource.getUserId(), consumeResult.getCash()));
        accountCreditRepository.save(new AccountCredit(billSource.getUserId(), accountCredit.getCredit(), accountCredit.getCreditMax()));

        //更新bill source
        billSource.setStatusCode(1L);
        billSourceRepository.updateStatusCode(billSource);

        return 1;
    }

    public void doRecharge(long userId) {
        RechargeSource rechargeSource = createRechargeSource(userId);
        long balance = rechargeSource.getAmount();

//        List<Trade> debtTrades = new ArrayList<>();

        // 查询欠款流水
//        for (; ; ) {
//            if (balance == 0) {
//                break;
//            }
//
//            debtTrades = tradeRepository.findDebt(userId);
//
//            if (CollectionUtils.isEmpty(debtTrades)) {
//                break;
//            }
//
//            // 欠费流水销账
//            for (Trade trade : debtTrades) {
//                RechargeResult rechargeResult = clearDebt(balance, trade.getDebt());
//                balance = rechargeResult.getCash();
//                tradeRepository.clearDebt(trade.getId(), trade.getDebt() - rechargeResult.getDebt(), rechargeResult.getDebt());
//
//                if (balance == 0) {
//                    break;
//                }
//            }
//        }

        // 补充信用额度
        // 现金账户充值
        AccountCash accountCash = accountCashRepository.getOne(rechargeSource.getUserId());
        AccountCredit accountCredit = accountCreditRepository.getOne(rechargeSource.getUserId());
        RechargeResult rechargeResult = calculationRecharge(rechargeSource.getAmount(), balance, accountCash.getCash(), accountCredit.getCredit(), accountCredit.getCreditMax());

        // 创建流水记录
        Trade trade = createTrade(4, rechargeSource.getRechargeId(), userId, rechargeResult);
        tradeRepository.insert(trade);

        //更新recharge source
        rechargeSource.setStatusCode(1L);
        rechargeSourceRepository.updateStatusCode(rechargeSource);
    }

    /**
     * 账户充值
     *
     * @param userIds
     * @return
     */
    public long doRecharge(Set<Long> userIds) {
        userIds.forEach(this::doRecharge);
        return 1L;
    }

    /**
     * 尝试消费一笔金额
     *
     * @param cash
     * @param credit
     * @param bill
     * @return
     */
    private ConsumeResult consume(long cash, long credit, long bill) {
        long cashConsume = 0;
        long creditConsume = 0;

        // 现金销账
        if (cash >= bill) {
            cashConsume = bill;
            cash -= bill;
            bill = 0;
        } else {
            cashConsume = cash;
            bill -= cash;
            cash = 0L;

        }

        // 信用账户销账
        if (bill != 0) {
            if (credit >= bill) {
                creditConsume = bill;
                credit -= bill;
                bill = 0;
            } else {
                creditConsume = credit;
                bill -= credit;
                credit = 0L;
            }
        }

        return new ConsumeResult(cashConsume, creditConsume, cash, credit, bill);
    }

    /**
     * 清除欠费金额
     *
     * @param balance
     * @param debt
     * @return
     */
    private RechargeResult clearDebt(long balance, long debt) {
        if (balance >= debt) {
            balance -= debt;
            debt = 0;
        } else {
            debt -= balance;
            balance = 0;
        }

        return new RechargeResult(balance, debt);
    }

    /**
     * 信用额度补偿,现金账户充值
     *
     * @param allBalance
     * @param balance
     * @param cash
     * @param credit
     * @param maxCredit
     * @return
     */
    private RechargeResult calculationRecharge(long allBalance, long balance, long cash, long credit, long maxCredit) {
        long cashConsume = 0;
        long creditConsume = 0;

        // 信用账户不满
        if (credit != maxCredit) {
            if (credit + balance > maxCredit) {
                creditConsume = maxCredit - credit;
                credit = maxCredit;
                balance -= creditConsume;
            } else {
                creditConsume = balance;
                credit += balance;
                balance = 0;
            }
        }

        cashConsume = allBalance - creditConsume;
        cash += balance;

        return new RechargeResult(cashConsume, creditConsume, cash, credit, 0);
    }

    /**
     * 创建一条订单
     *
     * @param sourceId
     * @param userId
     * @param consumeResult
     * @return
     */
    private Trade createTrade(int type, long sourceId, long userId, ConsumeResult consumeResult) {
        Trade trade = new Trade();
        trade.setId(Long.parseLong(snowflake.nextId()));

        trade.setType(type);
        trade.setSourceId(sourceId);
        trade.setCreateTime(new Date());
        trade.setUserId(userId);

        trade.setCash(consumeResult.getCashConsume());
        trade.setCashBalance(consumeResult.getCash());

        trade.setCredit(consumeResult.getCreditConsume());
        trade.setCreditBalance(consumeResult.getCredit());

        trade.setDebt(consumeResult.getDebt());

        return trade;
    }


    @Override
    public List<?> searchOrder() {
        int pageSize = (new Random().nextInt(3) + 1) * 10;
        int pageIndex = new Random().nextInt(100);

        int type = new Random().nextInt(4);
        long userId = new Random().nextInt((int) configData.getUserMax()) + 1;

        switch (type) {
            case 0:
                OrderSource orderSource = new OrderSource();
                Example<OrderSource> orderExample = Example.of(orderSource);

                orderSource.setUserId(userId);
                return orderSourceRepository.findAll(orderExample, PageRequest.of(pageIndex, pageSize)).getContent();
            case 1:
                Trade trade = new Trade();
                Example<Trade> tradeExample = Example.of(trade);

                trade.setUserId(userId);
                return tradeRepository.findAll(tradeExample, PageRequest.of(pageIndex, pageSize)).getContent();
            case 2:
                BillSource billSource = new BillSource();
                Example<BillSource> billSourceExample = Example.of(billSource);

                billSource.setUserId(userId);
                return billSourceRepository.findAll(billSourceExample, PageRequest.of(pageIndex, pageSize)).getContent();
            case 3:
                RechargeSource rechargeSource = new RechargeSource();
                Example<RechargeSource> rechargeSourceExample = Example.of(rechargeSource);

                rechargeSource.setUserId(userId);
                return rechargeSourceRepository.findAll(rechargeSourceExample, PageRequest.of(pageIndex, pageSize)).getContent();
        }

        return null;
    }

    @Override
    public List<?> searchStock() {
        int pageSize = (new Random().nextInt(3) + 1) * 10;
        int pageIndex = new Random().nextInt(100);

        long userId = new Random().nextInt((int) configData.getUserMax()) + 1;


        int type = new Random().nextInt(4);


        switch (type) {
            case 0:
                Product product = new Product();
                Example<Product> productExample = Example.of(product);

                long serviceId = new Random().nextInt((int) configData.getProductMax()) + 1;
                product.setId(serviceId);
                return productRepository.findAll(productExample, PageRequest.of(pageIndex, pageSize)).getContent();
            case 1:
                User user = new User();
                Example<User> userExample = Example.of(user);

                user.setId(0L);
                return userRepository.findAll(userExample, PageRequest.of(pageIndex, pageSize)).getContent();
            case 2:
                AccountCash accountCash = new AccountCash();
                Example<AccountCash> accountCashExample = Example.of(accountCash);

                accountCash.setId(userId);
                return accountCashRepository.findAll(accountCashExample, PageRequest.of(pageIndex, pageSize)).getContent();
            case 3:
                AccountCredit accountCredit = new AccountCredit();
                Example<AccountCredit> accountCreditExample = Example.of(accountCredit);

                accountCredit.setId(userId);
                return accountCreditRepository.findAll(accountCreditExample, PageRequest.of(pageIndex, pageSize)).getContent();
        }

        return null;
    }

}
