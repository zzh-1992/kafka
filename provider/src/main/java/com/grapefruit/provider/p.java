package com.grapefruit.provider;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class p {

    // 官网"生产者"链接 http://kafka.apache.org/documentation/#producerapi
    // api(java代码) http://kafka.apache.org/26/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html

    /**
     * 生产者日志
     */
    private final static Logger debugLogger = LoggerFactory.getLogger("PushDebugLogger");
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(10,10,60, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));

        for(int i = 0;i < 10;i++) {
            executor.execute(() -> {
                String msg = Thread.currentThread().getName() + ":sent a msg=======";
                producer.send(new ProducerRecord<>("grapefruit", "5353", msg));
                debugLogger.info(Thread.currentThread().getName() + ":sent a msg=======");
            });
        }
        Thread.sleep(1000);
        executor.shutdown();
        producer.close();
    }
}

// nothing
