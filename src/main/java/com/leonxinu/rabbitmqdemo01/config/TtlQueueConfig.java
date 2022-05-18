package com.leonxinu.rabbitmqdemo01.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author :leon
 * @date : 2022/5/17 21:13
 */
@Configuration
public class TtlQueueConfig {
    public static final String X_EXCHANGE = "X";
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_C = "QC";
    public static final String DEAD_LETTER_QUEUE = "QD";
    public static final String XA_ROUTING_KEY = "XA";
    public static final String XB_ROUTING_KEY = "XB";
    public static final String XC_ROUTING_KEY = "XC";
    public static final String DEAD_LETTER_ROUTING_KEY = "YD";

    /**
     * 声明 x 交换机
     *
     * @return
     */
    @Bean
    public DirectExchange xExchange() {
        return new DirectExchange(X_EXCHANGE);
    }

    /**
     * 声明 y 交换机
     *
     * @return
     */
    @Bean
    public DirectExchange yExchange() {
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }


    @Bean
    public Queue queueA() {
        return QueueBuilder.durable(QUEUE_A)
                .deadLetterExchange(Y_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(DEAD_LETTER_ROUTING_KEY)
                .ttl(10000)
                .build();
    }

    @Bean
    public Queue queueB() {
        return QueueBuilder.durable(QUEUE_B)
                .deadLetterExchange(Y_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(DEAD_LETTER_ROUTING_KEY)
                .ttl(40000)
                .build();
    }

    @Bean
    public Queue queueC() {
        return QueueBuilder.durable(QUEUE_C)
                .deadLetterExchange(Y_DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(DEAD_LETTER_ROUTING_KEY)
                .build();
    }

    /**
     * 声明死信队列
     *
     * @return
     */
    @Bean("queueD")
    public Queue queueD() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    /**
     * a 队列绑定 x 交换机
     *
     * @param queueA
     * @param xExchange
     * @return
     */
    @Bean
    public Binding queueABindingX(Queue queueA, DirectExchange xExchange) {
        return BindingBuilder.bind(queueA)
                .to(xExchange)
                .with(XA_ROUTING_KEY);
    }

    /**
     * b 队列绑定 x 交换机
     *
     * @param queueB
     * @param xExchange
     * @return
     */
    @Bean
    public Binding queueBBindingX(Queue queueB, DirectExchange xExchange) {
        return BindingBuilder.bind(queueB)
                .to(xExchange)
                .with(XB_ROUTING_KEY);
    }

    /**
     * c 队列绑定 x 交换机
     *
     * @param queueC
     * @param xExchange
     * @return
     */
    @Bean
    public Binding queueCBindingX(Queue queueC, DirectExchange xExchange) {
        return BindingBuilder.bind(queueC)
                .to(xExchange)
                .with(XC_ROUTING_KEY);
    }

    /**
     * d 队列绑定 y 交换机
     *
     * @param queueD
     * @param yExchange
     * @return
     */
    @Bean
    public Binding queueDBindingX(@Qualifier("queueD") Queue queueD, DirectExchange yExchange) {
        return BindingBuilder.bind(queueD)
                .to(yExchange)
                .with(DEAD_LETTER_ROUTING_KEY);
    }
}
