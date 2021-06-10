package com.wpj.paper.controller;

import com.wpj.paper.config.ConfigData;
import com.wpj.paper.dao.entity.BillSource;
import com.wpj.paper.dao.entity.OrderSource;
import com.wpj.paper.dao.entity.RechargeSource;
import com.wpj.paper.dao.repo.BillSourceRepository;
import com.wpj.paper.dao.repo.OrderSourceRepository;
import com.wpj.paper.dao.repo.ProductRepository;
import com.wpj.paper.dao.repo.RechargeSourceRepository;
import com.wpj.paper.exception.BizException;
import com.wpj.paper.service.BaseBizService;
import com.wpj.paper.service.plan.PlanService;
import com.wpj.paper.util.*;
import com.wpj.paper.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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

        try{
            return Result.ok(type.toString(), doService(service, type, planService));
        }catch (BizException bizException){
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
                // 执行实际业务
                return service.packageBill(userId, planService);
            case 2:
                userId = userZipf.next();
                // 执行实际业务
                return service.usageBill(userId, planService);
            case 3:
                // 执行随机查询业务
                return service.searchOrder();
            case 4:
                Set<Long> uIds = new HashSet<>();
                while (uIds.size() < configData.getBatchSize()) {
                    uIds.add((long) userZipf.next());
                }

                // 执行实际业务-账户充值
                return service.recharge(uIds, planService);
            case 5:
                Set<Long> pIds = productRepository.findInsufficient();

                // 执行实际业务-商品补货
                return service.reload(pIds, planService);
            case 6:
                // 执行随机查询业务
                return service.searchStock();
        }

        return "error";
    }

}
