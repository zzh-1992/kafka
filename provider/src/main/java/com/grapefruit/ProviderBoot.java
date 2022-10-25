/*
 * Copyright @2022 Grapefruit. All rights reserved.
 */

package com.grapefruit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 生产者主启动类
 *
 * @Author ZhangZhihuang
 * @Date 2022/7/9 09:07
 * @Version 1.0
 */
@SpringBootApplication
public class ProviderBoot {
    public static void main(String[] args) {
        SpringApplication.run(ProviderBoot.class);
    }
}
