package com.grapefruit.zzh.receiver;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author Grapefruit
 * @version 1.0
 * @date 2021/1/31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface DmqListener {
}
