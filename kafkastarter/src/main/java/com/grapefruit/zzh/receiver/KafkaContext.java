package com.grapefruit.zzh.receiver;

import com.grapefruit.zzh.log.LogTools;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Grapefruit
 * @version 1.0
 * @date 2021/1/31
 */
@Component
public class KafkaContext implements ApplicationContextAware {
    private final static Logger receiveLogger = LogTools.getReceiveLogger();
    @Autowired
    private KafkaConsumer consumer;

    private ApplicationContext context;

    private Map<String, Object> beanMap = new HashMap<>();
    private Map<String, Method> methodMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        getListener();
        listen();
    }

    public void getListener() {
        // 获取监听类
        Map<String, Object> beans = context.getBeansWithAnnotation(KafkaListener.class);
        beans.forEach((key, bean) -> {
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(KafkaMapping.class)) {
                    KafkaMapping annotation = method.getAnnotation(KafkaMapping.class);
                    String topic = annotation.topic();
                    beanMap.put(topic, bean);
                    methodMap.put(topic, method);
                }
            }
        });
    }

    // 起分支线程监听消息
    public void listen() {
        new Thread(() -> {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    // 获取监听类
                    Object bean = beanMap.get(record.topic());
                    // 获取监听方法
                    Method method = methodMap.get(record.topic());
                    // 获取参数类型
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    // 获取最后一个参数类型
                    Class<?> aClass = parameterTypes[parameterTypes.length - 1];
                    //method.invoke(bean,record.topic(), aClass.cast(record.value()));
                    handleReceive(method, bean, record, aClass);

                }
            }
        }).start();
    }

    /**
     * 调用方法消费，记录相关日志
     *
     * @param method
     * @param bean
     * @param record
     * @param aClass
     */
    public void handleReceive(Method method, Object bean, ConsumerRecord<String, String> record, Class<?> aClass) {
        try {
            Object cast = aClass.cast(record.value());
            method.invoke(bean, record.topic(), cast);
            receiveLogger.debug("context receive msg:{}", cast);

            consumer.commitAsync();
        } catch (IllegalAccessException | InvocationTargetException e) {
            receiveLogger.error("context handle error:{}", e.getMessage());
            e.printStackTrace();
        }
    }
}
