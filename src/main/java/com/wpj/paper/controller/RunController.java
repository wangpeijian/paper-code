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

import java.util.Random;

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


    @GetMapping(value = "/p1/{isolation}")
    public Result<?> p1(@PathVariable("isolation") String isolation) {
        int type = ActionFactor.getAction();
        long userId = Disperser.get(configData.getUserMax());

        BaseBizService service = getService(isolation);

        return Result.ok(doService(service, type, userId, dbPlanService));
    }

    @GetMapping(value = "/p2/{isolation}")
    public Result<?> p2(@PathVariable("isolation") String isolation) {
        int type = ActionFactor.getAction();
        long userId = Disperser.get(configData.getUserMax());

        BaseBizService service = getService(isolation);

        return Result.ok(doService(service, type, userId, redisPlanService));
    }

    @GetMapping(value = "/p3/{isolation}")
    public DeferredResult<Result<?>> p3(@PathVariable("isolation") String isolation) {
        int type = ActionFactor.getAction();
        long userId = Disperser.get(configData.getUserMax());

        BaseBizService service = getService(isolation);


        return (DeferredResult<Result<?>>) doService(service, type, userId, noLockPlanService);
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

    private Object doService(BaseBizService service, int type, long userId, PlanService<?> planService) {

        long price = new Random().nextInt((int) configData.getCashInit() / 10);

        long id;

        switch (type) {
            case 1:
                // 随机生成一条记录
                long serviceId = Disperser.get(configData.getProductMax());
                long num = new Random().nextInt((int) configData.getProductStockMax() / 100);

                id = Long.parseLong(snowflake.nextId());

                OrderSource source = new OrderSource(id, userId, id, serviceId, price, num);
                source = orderSourceRepository.save(source);

                // 执行实际业务
                return service.packageBill(source, planService);
            case 2:
                id = Long.parseLong(snowflake.nextId());

                // 随机生成一条记录
                BillSource billSource = new BillSource(id, userId, price, id);
                billSource = billSourceRepository.save(billSource);

                // 执行实际业务
                return service.usageBill(billSource, planService);
            case 3:
                // 执行随机查询业务
                return service.searchOrder();
            case 4:
                id = Long.parseLong(snowflake.nextId());

                // 随机生成一条记录
                RechargeSource rechargeSource = new RechargeSource(id, userId, price, id);
                rechargeSource = rechargeSourceRepository.save(rechargeSource);

                // 执行实际业务
                return service.recharge(rechargeSource, planService);
            case 5:
                // 执行随机查询业务
                return service.searchStock();
        }

        return "error" ;
    }


}
