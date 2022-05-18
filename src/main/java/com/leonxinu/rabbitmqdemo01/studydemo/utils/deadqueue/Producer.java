package com.leonxinu.rabbitmqdemo01.studydemo.utils.deadqueue;

import com.leonxinu.rabbitmqdemo01.studydemo.utils.RabbitmqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author :leon
 * @date : 2022/5/17 19:26
 */
@Slf4j
public class Producer {
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String NORMAL_ROUTING_KEY = "normal_routingKey";
    public static final Integer BATCH_SIZE = 10;
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        channel.queueDeclare(NORMAL_EXCHANGE,false,false,false,null);
        AMQP.BasicProperties properties = new AMQP.BasicProperties()
                .builder()
                .expiration("10000")
                .build();
        for (int i = 0; i < BATCH_SIZE; i++) {
            String message = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE,NORMAL_ROUTING_KEY,properties,message.getBytes());
            log.info("消息发送成功...");
        }
    }
}
