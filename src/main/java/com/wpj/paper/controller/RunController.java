package com.wpj.paper.controller;

import com.wpj.paper.config.ConfigData;
import com.wpj.paper.dao.repo.normal.BillSourceRepository;
import com.wpj.paper.dao.repo.normal.OrderSourceRepository;
import com.wpj.paper.dao.repo.normal.ProductRepository;
import com.wpj.paper.dao.repo.normal.RechargeSourceRepository;
import com.wpj.paper.exception.BizException;
import com.wpj.paper.service.BaseBizService;
import com.wpj.paper.service.plan.PlanService;
import com.wpj.paper.util.ActionFactor;
import com.wpj.paper.util.LockPlanRecord;
import com.wpj.paper.util.Snowflake;
import com.wpj.paper.util.ZipfGenerator;
import com.wpj.paper.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.LockAcquisitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.CannotSerializeTransactionException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

@Slf4j
@RestController
@RequestMapping(value = "/run")
public class RunController {

    @Autowired
    BillSourceRepository billSourceRepository;

    @Autowired
    OrderSourceRepository orderSourceRepository;

    @Autowired
    RechargeSourceRepository rechargeSourceRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BaseBizService ssi;

    @Autowired
    BaseBizService rr;

    @Autowired
    BaseBizService rc;

    @Autowired
    BaseBizService ru;

    @Autowired
    Snowflake snowflake;

    @Autowired
    ConfigData configData;

    @Qualifier("dbPlan")
    @Autowired
    PlanService dbPlanService;

    @Qualifier("redisPlan")
    @Autowired
    PlanService redisPlanService;

    @Qualifier("javaLockPlan")
    @Autowired
    PlanService javaLockPlanService;

    @Autowired
    ZipfGenerator userZipf;

    @Autowired
    LockPlanRecord lockPlanRecord;


    @Value("${spring.profiles.active}")
    private String activeProfile;


    @GetMapping(value = "/preheat")
    public Result<?> preheat() {
        int type = 3;
        if (new Random().nextInt(2) == 1) {
            type = 6;
        }
        BaseBizService service = getService("ru");

        return Result.ok(doService(service, type, dbPlanService));
    }

    @GetMapping(value = "/{pType}/{isolation}")
    public Result<?> execute(@PathVariable("pType") String pType, @PathVariable("isolation") String isolation) {
        Integer type = ActionFactor.getAction();

        BaseBizService service = getService(isolation);
        assert service != null;
        lockPlanRecord.setCode(String.format("%s-%s", activeProfile, service.getBeanName()));

        PlanService<?> planService = getPlanService(pType);

        try {
            return Result.ok(type.toString(), doService(service, type, planService));
        } catch (BizException bizException) {
            log.info("biz error", bizException);
            return Result.error(bizException.getMessage());
        }
    }

    private PlanService<?> getPlanService(String pType) {
        switch (pType) {
            case "p1":
                return dbPlanService;
            case "p2":
                return redisPlanService;
            case "p3":
                return javaLockPlanService;
        }
        return null;
    }

    private BaseBizService getService(String isolation) {
        switch (isolation) {
            case "ssi":
                return ssi;
            case "rr":
                return rr;
            case "rc":
                return rc;
            case "ru":
                return ru;
        }

        return null;
    }

    private Object doService(BaseBizService service, int type, PlanService<?> planService) {

        long userId;

        switch (type) {
            case 1:
                userId = userZipf.next();
                // 执行包费购买
                return doTask(2000, () -> service.packageBill(userId, planService));
            case 2:
                userId = userZipf.next();
                // 执行按量销账
                return doTask(2000, () -> service.usageBill(userId, planService));
            case 3:
                // 执行随机查询业务
                return service.searchOrder();
            case 4:
                // 执行账户充值
                return doTask(2000, () -> service.recharge(planService));
            case 5:
                // 执行库存补货
                return doTask(2000, () -> service.reload(planService));
            case 6:
                // 执行随机查询业务
                return service.searchStock();
        }

        return "error";
    }

    private Object doTask(long timeout, Supplier<Object> supplier) {

        if(lockPlanRecord.getCode().startsWith("pgsql") || lockPlanRecord.getCode().startsWith("mysql")){
            long endTime = System.currentTimeMillis() + timeout;

            do {
                // 超时
                if (System.currentTimeMillis() + 20 >= endTime) {
                    throw new RuntimeException("串行化重试超时");
                }

                try {
                    return supplier.get();
                } catch (CannotAcquireLockException | CannotSerializeTransactionException | LockAcquisitionException | JpaSystemException e) {
                    if (e.getClass().equals(CannotAcquireLockException.class)) {
                        log.error("pgsql 事务并发失败重试操作", e);
                    } else {
                        log.error("pgsql ssi事务串行化失败重试操作", e);
                    }

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }

            } while (true);
        }else {
            return supplier.get();
        }
    }

}
