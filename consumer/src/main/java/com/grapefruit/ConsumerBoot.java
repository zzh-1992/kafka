/*
 * Copyright @2022 Grapefruit. All rights reserved.
 */

package com.grapefruit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 消费者主启动类
 *
 * @Author ZhangZhihuang
 * @Date 2022/7/9 09:07
 * @Version 1.0
 */
@SpringBootApplication
public class ConsumerBoot {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerBoot.class);
    }
}
