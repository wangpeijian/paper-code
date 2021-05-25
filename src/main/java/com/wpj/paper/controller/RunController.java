package com.wpj.paper.controller;

import com.wpj.paper.config.ConfigData;
import com.wpj.paper.dao.entity.BillSource;
import com.wpj.paper.dao.entity.OrderSource;
import com.wpj.paper.dao.entity.RechargeSource;
import com.wpj.paper.dao.repo.BillSourceRepository;
import com.wpj.paper.dao.repo.OrderSourceRepository;
import com.wpj.paper.dao.repo.RechargeSourceRepository;
import com.wpj.paper.service.BaseBizService;
import com.wpj.paper.service.plan.PlanService;
import com.wpj.paper.util.ActionFactor;
import com.wpj.paper.util.Disperser;
import com.wpj.paper.util.Snowflake;
import com.wpj.paper.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Qualifier("noLockPlan")
    @Autowired
    PlanService noLockPlanService;


    @GetMapping(value = "/preheat")
    public Result<?> preheat() {
        int type = 4;
        if (new Random().nextInt(2) == 1) {
            type = 5;
        }
        BaseBizService service = getService("ru");

        return Result.ok(doService(service, type, dbPlanService));
    }

    @GetMapping(value = "/p1/{isolation}")
    public Result<?> p1(@PathVariable("isolation") String isolation) {
        int type = ActionFactor.getAction();

        BaseBizService service = getService(isolation);

        return Result.ok(doService(service, type, dbPlanService));
    }

    @GetMapping(value = "/p2/{isolation}")
    public Result<?> p2(@PathVariable("isolation") String isolation) {
        int type = ActionFactor.getAction();

        BaseBizService service = getService(isolation);

        return Result.ok(doService(service, type, redisPlanService));
    }

    @GetMapping(value = "/p3/{isolation}")
    public DeferredResult<Result<?>> p3(@PathVariable("isolation") String isolation) {
        int type = ActionFactor.getAction();

        BaseBizService service = getService(isolation);

        return (DeferredResult<Result<?>>) doService(service, type, noLockPlanService);
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

        log.info("doService type: {}", type);

        long userId;

        switch (type) {
            case 1:
                userId = Disperser.get(configData.getUserMax());
                // 执行实际业务
                return service.packageBill(userId, planService);
            case 2:
                userId = Disperser.get(configData.getUserMax());
                // 执行实际业务
                return service.usageBill(userId, planService);
            case 3:
                // 执行随机查询业务
                return service.searchOrder();
            case 4:

                int max = new Random().nextInt(10) + 1;
                Set<Long> ids = new HashSet<>();
                while (ids.size() < max) {
                    ids.add(Disperser.get(configData.getUserMax()));
                }

                // 执行实际业务
                return service.recharge(ids, planService);
            case 5:
                // 执行随机查询业务
                return service.searchStock();
        }

        return "error";
    }


}
