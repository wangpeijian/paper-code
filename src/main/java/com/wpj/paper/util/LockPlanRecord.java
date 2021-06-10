package com.wpj.paper.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class LockPlanRecord {

    ThreadLocal<LockPlan> planThreadLocal = new ThreadLocal<>();

    public String getCode() {
        if (planThreadLocal.get() == null) {
            return null;
        }
        return planThreadLocal.get().getCode();
    }

    public void setCode(String code) {
        planThreadLocal.set(new LockPlan(code));
    }

    @AllArgsConstructor
    @Data
    public static class LockPlan {
        private String code;
    }

}
