package com.grapefruit.provider;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class p {

    // 官网"生产者"链接 http://kafka.apache.org/documentation/#producerapi
    // api(java代码) http://kafka.apache.org/26/javadoc/index.html?org/apache/kafka/clients/producer/KafkaProducer.html

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        //for (int i = 0; i < 100; i++)
        //producer.send(new ProducerRecord<String, String>("grapefruit", "Hello", Integer.toString(i) + "====="));

        producer.send(new ProducerRecord<String, String>("grapefruit", "Hello", "consumer a msg===2========" ));

        producer.close();
    }
}

// nothing
