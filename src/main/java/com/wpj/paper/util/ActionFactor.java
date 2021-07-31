package com.wpj.paper.util;

import java.util.Random;

public class ActionFactor {

    /**
     * 根据百分比确定当前请求处理那种操作
     * <p>
     * 1: 45%  客户完成一笔包费订单
     * 2: 43%  完成一笔按量交易的账单销账
     * 3: 4%   查询客户某一分页的交易流水
     * 4: 3%   完成一批客户的账户充值
     * 5: 1%   完成库存最少的10个商品补货
     * 6: 4%   查询商品库存量最少的10种货品等
     *
     * @return
     */
    public static Integer getAction() {
        int percent = new Random().nextInt(100);
        if (percent < 45) {
            return 1;
        } else if (percent < 88) {
            return 2;
        } else if (percent < 92) {
            return 3;
        } else if (percent < 95) {
            return 4;
        } else if (percent < 96) {
            return 5;
        } else {
            return 6;
        }
    }

}
