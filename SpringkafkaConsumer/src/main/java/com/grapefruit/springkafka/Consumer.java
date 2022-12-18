package com.grapefruit.springkafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


/**
 * kafka消费者
 *
 * @author Grapefruit
 * @version 1.0
 * @date 2022/12/18
 */
@Service
@Slf4j(topic = "ReceiveLogger")
public class Consumer {

    @KafkaListener(topics = {"grapefruit"})
    public void onMessage(String topic, String key, String message) {
        log.debug("receive key:{}, msg:{}", key, message);
        System.out.println("SpringKafka consumer receive msg:" + message);
    }
}
