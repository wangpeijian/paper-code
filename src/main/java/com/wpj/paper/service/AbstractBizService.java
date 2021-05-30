package com.wpj.paper.service;

import com.wpj.paper.config.ConfigData;
import com.wpj.paper.dao.entity.*;
import com.wpj.paper.dao.repo.*;
import com.wpj.paper.service.model.ConsumeResult;
import com.wpj.paper.service.model.RechargeResult;
import com.wpj.paper.service.plan.PlanService;
import com.wpj.paper.util.Disperser;
import com.wpj.paper.util.Snowflake;
import com.wpj.paper.util.ZipfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    OrderItemRepository orderItemRepository;

    @Autowired
    Snowflake snowflake;

    @Autowired
    ConfigData configData;

    @Autowired
    ZipfGenerator userZipf;

    @Autowired
    ZipfGenerator productZipf;

    public BillSource createBillSource(long userId) {
        long id = Long.parseLong(snowflake.nextId());
        long price = new Random().nextInt((int) configData.getCashInit() / 100);

        // 随机生成一条记录
        BillSource billSource = new BillSource(id, userId, price, id);
        billSourceRepository.insert(billSource);
        return billSource;
    }

    public RechargeSource createRechargeSource(long userId) {
        long id = Long.parseLong(snowflake.nextId());
        long price = new Random().nextInt((int) configData.getCashInit());

        // 随机生成一条记录
        RechargeSource rechargeSource = new RechargeSource(id, userId, price, id);
        rechargeSourceRepository.insert(rechargeSource);
        return rechargeSource;
    }

    public OrderSource createOrderSource(long userId) {
        long id = Long.parseLong(snowflake.nextId());

        // 随机生成一条记录
        OrderSource source = new OrderSource(id, userId, id, 0L);
        orderSourceRepository.insert(source);
        return source;
    }

    /**
     * 创建订单详情表
     *
     * @param orderSource
     * @param products
     * @return
     */
    public List<OrderItem> createOrderItems(OrderSource orderSource, HashMap<Long, Integer> products) {
        List<OrderItem> orderItems = new ArrayList<>();

        products.forEach((key, value) -> {
            long id = Long.parseLong(snowflake.nextId());
            long pid = key;
            int num = value;
            Product product = productRepository.getOne(pid);
            int success = productRepository.deductStock(pid, (long) num);

            if (success == 1) {
                BigDecimal discount = product.getDiscount();
                long price = new BigDecimal(product.getOriginalPrice().toString()).multiply(discount).multiply(BigDecimal.valueOf(num)).longValue();
                OrderItem orderItem = new OrderItem(id, orderSource.getOrderId(), pid, num, price, discount);
                orderItems.add(orderItem);
            }

        });

        orderItemRepository.saveAll(orderItems);
        return orderItems;
    }

    /**
     * 支付包费订单
     *
     * @return
     */
    public Object doPackageBill(long userId, PlanService<?> planService) {

        return planService.lockUser(userId, () -> {

            HashMap<Long, Integer> products = new HashMap<>();

            for (int i = 0; i < new Random().nextInt(10) + 1; i++) {
                Long pid = (long) productZipf.next();
                Integer num = Optional.ofNullable(products.get(pid)).orElse(0);
                products.put(pid, ++num);
            }

            planService.lockProduct(products.keySet(), () -> {
                OrderSource orderSource = createOrderSource(userId);
                List<OrderItem> orderItems = createOrderItems(orderSource, products);

                // 余额足够,尝试扣减库存
                if (orderItems.size() == 0) {
                    throw new RuntimeException("库存不足");
                }

                //更新order source
                long price = orderItems.stream().mapToLong(OrderItem::getPrice).sum();
                orderSource.setPrice(price);
                orderSource.setStatusCode(1L);
                orderSourceRepository.updateStatusCodeAndPrice(orderSource);

                AccountCash accountCash = accountCashRepository.getOne(userId);
                AccountCredit accountCredit = accountCreditRepository.getOne(userId);

                // 扣减账单
                ConsumeResult consumeResult = consume(accountCash.getCash(), accountCredit.getCredit(), orderSource.getPrice());

                // 余额不足直接返回
                if (consumeResult.hasDebt()) {
                    throw new RuntimeException("余额不足");
                }

                // 创建流水记录
                Trade trade = createTrade(1, orderSource.getOrderId(), userId, consumeResult);
                tradeRepository.insert(trade);

                // 更新账户金额
                accountCashRepository.save(new AccountCash(userId, consumeResult.getCash()));
                accountCreditRepository.save(new AccountCredit(userId, accountCredit.getCredit(), accountCredit.getCreditMax()));

                return 1L;
            });

            return 1L;
        });
    }

    /**
     * 支付按量付费账单
     *
     * @param userId
     * @param planService
     * @return
     */
    public Object doUsageBill(long userId, PlanService<?> planService) {
        return planService.lockUser(userId, () -> {
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

            return 1L;
        });

    }

    public void doRecharge(long userId) {
        RechargeSource rechargeSource = createRechargeSource(userId);
        long balance = rechargeSource.getAmount();

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
     * @param planService
     * @return
     */
    public Object doRecharge(Set<Long> userIds, PlanService<?> planService) {
        return planService.lockUser(userIds, () -> {
            userIds.forEach(this::doRecharge);
            return 1L;
        });
    }

    public void doReload(long pId) {
        Product product = productRepository.getOne(pId);
        long reload = configData.getProductStockMax() - product.getStock();
        productRepository.reload(pId, reload);
    }

    /**
     * 商品库存补货
     *
     * @param pIds
     * @param planService
     * @return
     */
    public Object doReload(Set<Long> pIds, PlanService<?> planService) {
        return planService.lockProduct(pIds, () -> {
            pIds.forEach(this::doReload);
            return 1L;
        });
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

        trade.setCredit(consumeResult.getCreditConsume());

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
