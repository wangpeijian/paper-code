package com.wpj.paper.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.wpj.paper.util.Snowflake;
import com.wpj.paper.util.ZipfGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

@Slf4j
@Configuration
public class BeanConfiguration {

    @Autowired
    ConfigData configData;

    @Bean
    public Snowflake snowflake() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            String[] ipArr = ip.getHostAddress().split("\\.");

            log.info("初始化雪花生成器 workerId: [{}] dataCenterId: [{}]", ipArr[3], 1);
            return new Snowflake(Long.parseLong(ipArr[3]), 1);
        } catch (UnknownHostException e) {
            log.error("获取本地ip地址失败:", e);
        }

        // 使用ip初始化失败,改为使用随机数方案初始化id生成器,随机数方式主机id为0
        log.info("使用ip初始化失败,改为使用随机数方案初始化id生成器");
        return new Snowflake(new Random().nextInt(128), 0);
    }

    @Bean
    public ZipfGenerator userZipf() {
        return new ZipfGenerator((int) configData.getUserMax(), 0.9);
    }

    @Bean
    public ZipfGenerator productZipf() {
        log.info("商品库zipf系数: [{}]", configData.getProductZipf());
        return new ZipfGenerator((int) configData.getProductMax(), configData.getProductZipf());
    }


    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid() {
        return new DruidDataSource();
    }


}
