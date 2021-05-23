package com.wpj.paper.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RechargeResult extends ConsumeResult{

    public RechargeResult(long cash, long debt){
        super();
        this.cash = cash;
        this.debt = debt;
    }

    private long cashConsume = 0;
    private long creditConsume = 0;

    private long cash;
    private long credit;
    private long debt;
}
