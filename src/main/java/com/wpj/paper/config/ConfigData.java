package com.wpj.paper.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ConfigData {

    @Value("${config.user-max}")
    private long userMax;

    @Value("${config.cash-init}")
    private long cashInit;

    @Value("${config.credit-init}")
    private long creditInit;

    @Value("${config.product-max}")
    private long productMax;

    @Value("${config.product-stock-max}")
    private long productStockMax;

    @Value("${config.product-zipf}")
    private float productZipf;

    @Value("${config.pool-size}")
    private int poolSize;

    @Value("${config.batch-size}")
    private int batchSize;
}
