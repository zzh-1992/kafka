package com.grapefruit.zzh.receiver;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * kafka扫描类
 * @author Grapefruit
 * @version 1.0
 * @date 2021/1/31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface KafkaListener {
}
