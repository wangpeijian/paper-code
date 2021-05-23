package com.wpj.paper.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class Disperser {

    public static long get(long max) {
        return get(max, 0.2f);
    }

    /**
     * 1-factor 概率的数据分散到 max * factor 的范围
     * factor 概率的数据分散到 max * (1-factor) 的范围
     *
     * @param max
     * @param factor
     * @return
     */
    public static long get(long max, float factor) {
        float part = new Random().nextInt(10);
        if (part >= factor * 10) {
            return new Random().nextInt((int) (max * factor)) + 1;
        } else {
            return (long) (new Random().nextInt((int) (max * (1 - factor))) + (max * factor)) + 1;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            log.info("i={}, r={}", i, get(100));
        }
    }
}
