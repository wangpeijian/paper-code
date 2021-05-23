package com.wpj.paper.service;

import com.wpj.paper.dao.entity.*;
import com.wpj.paper.dao.repo.*;
import com.wpj.paper.service.model.ConsumeResult;
import com.wpj.paper.service.model.RechargeResult;
import com.wpj.paper.util.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

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
    Snowflake snowflake;


    /**
     * 支付按量付费账单
     *
     * @param billSource
     * @return
     */
    public long doUsageBill(BillSource billSource) {
        AccountCash accountCash = accountCashRepository.getOne(billSource.getUserId());
        AccountCredit accountCredit = accountCreditRepository.getOne(billSource.getUserId());

        // 扣减账单
        ConsumeResult consumeResult = consume(accountCash.getCash(), accountCredit.getCredit(), billSource.getAmount());

        // 创建流水记录
        Trade trade = createTrade(2, billSource.getBillId(), billSource.getUserId(), consumeResult);
        tradeRepository.save(trade);

        // 更新账户金额
        accountCashRepository.save(new AccountCash(billSource.getUserId(), consumeResult.getCash()));
        accountCreditRepository.save(new AccountCredit(billSource.getUserId(), accountCredit.getCredit(), accountCredit.getCreditMax()));

        //更新bill source
        billSource.setStatusCode(1L);
        billSourceRepository.save(billSource);

        return 1;
    }

    /**
     * 支付包费订单
     *
     * @return
     */
    public long doPackageBill(OrderSource orderSource) {
        long userId = orderSource.getUserId();
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
        tradeRepository.save(trade);

        // 更新账户金额
        accountCashRepository.save(new AccountCash(userId, consumeResult.getCash()));
        accountCreditRepository.save(new AccountCredit(userId, accountCredit.getCredit(), accountCredit.getCreditMax()));

        //更新order source
        orderSource.setStatusCode(errorCode);
        orderSourceRepository.save(orderSource);

        return errorCode;
    }

    /**
     * 账户充值
     *
     * @return
     */
    public long doRecharge(RechargeSource rechargeSource) {
        long userId = rechargeSource.getUserId();
        long balance = rechargeSource.getAmount();

        List<Trade> debtTrades;

        // 查询欠款流水
        for (; ; ) {
            if (balance == 0) {
                break;
            }

            debtTrades = tradeRepository.findDebt(userId);

            if (CollectionUtils.isEmpty(debtTrades)) {
                break;
            }

            // 欠费流水销账
            for (Trade trade : debtTrades) {
                RechargeResult rechargeResult = clearDebt(balance, trade.getDebt());
                balance = rechargeResult.getCash();
                tradeRepository.clearDebt(trade.getId(), trade.getDebt() - rechargeResult.getDebt(), rechargeResult.getDebt());

                if (balance == 0) {
                    break;
                }
            }
        }

        // 补充信用额度
        // 现金账户充值
        AccountCash accountCash = accountCashRepository.getOne(rechargeSource.getUserId());
        AccountCredit accountCredit = accountCreditRepository.getOne(rechargeSource.getUserId());
        RechargeResult rechargeResult = calculationRecharge(rechargeSource.getAmount(), balance, accountCash.getCash(), accountCredit.getCredit(), accountCredit.getCreditMax());

        // 创建流水记录
        Trade trade = createTrade(4, rechargeSource.getRechargeId(), userId, rechargeResult);
        tradeRepository.save(trade);

        //更新recharge source
        rechargeSource.setStatusCode(1L);
        rechargeSourceRepository.save(rechargeSource);

        return 1;
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
    public List<OrderSource> searchOrder() {

        return null;
    }

    @Override
    public List<Product> searchStock() {

        return null;
    }

}
