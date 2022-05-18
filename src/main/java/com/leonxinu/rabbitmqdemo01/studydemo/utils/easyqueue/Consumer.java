package com.leonxinu.rabbitmqdemo01.studydemo.utils.easyqueue;

import com.leonxinu.rabbitmqdemo01.studydemo.utils.RabbitmqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author :leon
 * @date : 2022/5/13 21:43
 */
@Slf4j
public class Consumer {
    public static final String NORMAL_QUEUE = "normal_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        log.info("等待接收消息...");
        DeliverCallback deliverCallback = (consumerTag, message) -> System.out.println("Consumer02接收到的消息是：" + new String(message.getBody(),"UTF-8"));
        channel.basicConsume(NORMAL_QUEUE, true, deliverCallback, (CancelCallback) null);
    }
}
