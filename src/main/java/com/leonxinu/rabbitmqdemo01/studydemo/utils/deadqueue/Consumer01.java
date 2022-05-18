package com.leonxinu.rabbitmqdemo01.studydemo.utils.deadqueue;

import com.leonxinu.rabbitmqdemo01.studydemo.utils.RabbitmqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author :leon
 * @date : 2022/5/17 19:26
 */
@Slf4j
public class Consumer01 {
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String NORMAL_QUEUE = "normal_queue";
    public static final String DEAD_QUEUE = "dead_queue";
    public static final String DEAD_ROUTING_KEY = "dead_routingKey";
    public static final String NORMAL_ROUTING_KEY = "normal_routingKey";
    public static final  Integer MAX_LENGTH = 6;

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        // 声明交换机
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        // 声明普通队列 设置参数 arguments
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", DEAD_ROUTING_KEY);
        // 设置了队列长度为6
        arguments.put("x-max-length", MAX_LENGTH);
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);

        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);
        // 普通交换机和队列进行绑定
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, NORMAL_ROUTING_KEY);
        // 死信交换机和队列进行绑定
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, DEAD_ROUTING_KEY);
        log.info("等待接收消息.....");

        DeliverCallback deliverCallback = (consumerTag, message) -> System.out.println(" Consumer01接收到的消息是：" + new String(message.getBody(),"UTF-8"));

        channel.basicConsume(NORMAL_QUEUE, true, deliverCallback, (CancelCallback) null);
    }
}
