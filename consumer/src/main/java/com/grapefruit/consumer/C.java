package com.grapefruit.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

public class C {

    // 官网"消费者"链接 http://kafka.apache.org/documentation/#consumerapi
    // api(java代码) http://kafka.apache.org/26/javadoc/index.html?org/apache/kafka/clients/consumer/KafkaConsumer.html

    private final static Logger debugLogger = LoggerFactory.getLogger("debug");
    private final static Logger errorLogger = LoggerFactory.getLogger("error");
    public static void main(String[] args) throws IOException {

        //FileOutputStream fs = new FileOutputStream("ConsumerLog",true);

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("grapefruit", "bar"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                String s = new SimpleDateFormat("yyy/MM/dd HH:mm:ss").format(new Date()) + " offset:" +  record.offset() + " key:" + record.key() + " value:" +  record.value();

                debugLogger.debug("offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
                errorLogger.debug("error msg, offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
                errorLogger.info("info msg, offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
                //fs.write(s.getBytes());
            }
        }
    }
}
