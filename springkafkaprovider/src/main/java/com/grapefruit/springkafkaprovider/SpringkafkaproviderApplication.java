package com.grapefruit.springkafkaprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class SpringkafkaproviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringkafkaproviderApplication.class, args);
    }

    @Autowired
    KafkaTemplate<String, String> template;

    @Bean
    public ApplicationRunner runner() {
        return (args) -> {
            template.send("grap    efruit", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " try to use springkafka to send msg");
            //template.executeInTransaction(t -> t.send("grapefruit", "try to use springkafka to send msg 2"));
        };
    }
}
