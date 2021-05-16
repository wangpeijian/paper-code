package com.wpj.paper.config;

import com.wpj.paper.service.BaseBizService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BizFactory {

    @Value("${bizImplCode}")
    private String bizImplCode;

    @Bean
    public BaseBizService bizService(ApplicationContext applicationContext) {
        return applicationContext.getBean(bizImplCode, BaseBizService.class);
    }

}
