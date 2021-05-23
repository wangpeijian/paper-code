package com.wpj.paper.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ConsumeResult {
    public boolean hasDebt(){
        return debt > 0;
    }

    public ConsumeResult(){

    }

    private long cashConsume = 0;
    private long creditConsume = 0;

    private long cash;
    private long credit;
    private long debt;
}
