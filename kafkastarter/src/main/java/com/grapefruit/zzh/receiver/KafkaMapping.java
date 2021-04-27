package com.grapefruit.zzh.receiver;

import java.lang.annotation.*;

/**
 * kafka方法调用类
 * @author Grapefruit
 * @version 1.0
 * @date 2021/1/31
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KafkaMapping {
    String topic() default "grapefruit";
}
