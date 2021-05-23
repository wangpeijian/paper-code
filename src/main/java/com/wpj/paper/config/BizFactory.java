package com.wpj.paper.config;

import com.wpj.paper.service.BaseBizService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BizFactory {

    @Value("${isolation.ssi}")
    private String ssiCode;

    @Value("${isolation.rr}")
    private String rrCode;

    @Value("${isolation.rc}")
    private String rcCode;

    @Value("${isolation.ru}")
    private String ruCode;

    @Bean("ssi")
    public BaseBizService ssi(ApplicationContext applicationContext) {
        return applicationContext.getBean(ssiCode, BaseBizService.class);
    }

    @Bean("rr")
    public BaseBizService rr(ApplicationContext applicationContext) {
        return applicationContext.getBean(rrCode, BaseBizService.class);
    }

    @Bean("rc")
    public BaseBizService rc(ApplicationContext applicationContext) {
        return applicationContext.getBean(rcCode, BaseBizService.class);
    }

    @Bean("ru")
    public BaseBizService ru(ApplicationContext applicationContext) {
        return applicationContext.getBean(ruCode, BaseBizService.class);
    }

}
