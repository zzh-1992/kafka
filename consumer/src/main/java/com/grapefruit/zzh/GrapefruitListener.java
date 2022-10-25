package com.grapefruit.zzh;

import com.grapefruit.utils.log.LogTools;
import com.grapefruit.zzh.receiver.KafkaListener;
import com.grapefruit.zzh.receiver.KafkaMapping;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * kafka消费者(普通接口调用及MQ消息框架调用)
 *
 * @author Grapefruit
 * @version 1.0
 * @date 2021/1/31
 */
@KafkaListener
@RequestMapping("/")
@RestController
public class GrapefruitListener {
    private final static Logger log = LogTools.getReceiveLogger();

    @KafkaMapping(topic = "grapefruit")
    @GetMapping("/onMessage")
    public void onMessage(String topic, String key, String message) {
        log.debug("receive key:{}, msg:{}", key, message);
        System.out.println("receive msg:" + message);
    }
}
