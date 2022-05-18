package com.leonxinu.rabbitmqdemo01.studydemo.utils.topicexchange;

import com.leonxinu.rabbitmqdemo01.studydemo.utils.RabbitmqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author :leon
 * @date : 2022/5/17 16:43
 */
@Slf4j
public class Consumer02 {
    public static final String EXCHANGE_NAME = "topic_logs";
    public static final String QUEUE_NAME = "Q2";
    public static final String RABBIT = "*.*.rabbit";
    public static final String LAZY = "lazy.#";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        // 声明交换机 模式为 topic
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 绑定队列和交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, RABBIT);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, LAZY);
        log.info("等待接收消息.....");
        // 开始接收消息
        DeliverCallback deliverCallback = (consumerTag, message) -> System.out.println("接收队列：" + QUEUE_NAME +
                " 绑定键：" + message.getEnvelope().getRoutingKey() +
                " 接收到的消息是：" + new String(message.getBody()));

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, (CancelCallback) null);
    }
}
