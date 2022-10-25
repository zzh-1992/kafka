/*
 *Copyright @2022 Grapefruit. All rights reserved.
 */

package com.grapefruit.zzh.sender;

import com.grapefruit.utils.log.LogTools;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class KafkaSender {
    @Autowired
    private Producer<String, String> producer;
    private static final Logger sendLogger = LogTools.getSendLogger();

    public KafkaSender() {
    }

    public String send(String msg) {
        Future<RecordMetadata> future = this.producer.send(new ProducerRecord("grapefruit", "5353", msg));
        sendLogger.info("sent msg:{}", msg);
        return future.toString();
    }
}