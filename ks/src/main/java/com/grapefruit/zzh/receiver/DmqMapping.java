package com.grapefruit.zzh.receiver;

import java.lang.annotation.*;

/**
 * @author Grapefruit
 * @version 1.0
 * @date 2021/1/31
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DmqMapping {

    String topic() default "grapefruit";
}
