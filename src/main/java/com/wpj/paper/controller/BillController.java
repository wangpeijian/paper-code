package com.wpj.paper.controller;

import com.wpj.paper.dao.entity.BillSource;
import com.wpj.paper.service.BaseBizService;
import com.wpj.paper.service.BillService;
import com.wpj.paper.util.ThreadExecutor;
import com.wpj.paper.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@Slf4j
@RestController
@RequestMapping(value = "/bill")
public class BillController {

    @Autowired
    BillService billService;

    ThreadExecutor threadExecutor = new ThreadExecutor(16);

    @Autowired
    BaseBizService bizService;

    /**
     * 串行话隔离级别
     *
     * @return
     */
    @GetMapping(value = "/test")
    public Result<?> test(@PathVariable("mockId") long mockId) {
        // 查找mock数据
        BillSource billSource = billService.findBillSource(mockId);
        return Result.ok(bizService.usageBill(billSource));
    }

    /**
     * 串行话隔离级别
     *
     * @param mockId
     * @return
     */
    @GetMapping(value = "/ssi/{mockId}")
    public Result<?> ssi(@PathVariable("mockId") long mockId) {
        // 查找mock数据
        BillSource billSource = billService.findBillSource(mockId);

        boolean success = billService.trade(billSource);

        Result<?> result;

        if (success) {
            result = Result.ok(null);
        } else {
            result = Result.error("销账失败");
        }

        return result;
    }

    /**
     * ru/rc/rr - redis
     *
     * @param mockId
     * @return
     */
    @GetMapping(value = "/redis/{mockId}")
    public Result<?> redis(@PathVariable("mockId") long mockId) {
        // 查找mock数据
        BillSource billSource = billService.findBillSource(mockId);

        boolean success = billService.trade(billSource);

        Result<?> result;

        if (success) {
            result = Result.ok(null);
        } else {
            result = Result.error("销账失败");
        }

        return result;
    }

    /**
     * ru/rc/rr - thread + redis
     *
     * @param mockId
     * @return
     */
    @GetMapping(value = "/thread-and-redis/{mockId}")
    public DeferredResult<Result<?>> threadAndRedis(@PathVariable("mockId") long mockId) {

        DeferredResult<Result<?>> deferredResult = new DeferredResult<>(20000L, () -> Result.error("timeout"));

        // 查找mock数据
        BillSource billSource = billService.findBillSource(mockId);

        if (billSource == null) {
            deferredResult.setResult(Result.error("数据不存在"));
        } else {
            threadExecutor.submit(billSource.getUserId(), () -> {
                boolean success = billService.trade(billSource);

                Result<?> result;

                if (success) {
                    result = Result.ok(null);
                } else {
                    result = Result.error("销账失败");
                }

                deferredResult.setResult(result);
            });
        }

        return deferredResult;
    }
}
