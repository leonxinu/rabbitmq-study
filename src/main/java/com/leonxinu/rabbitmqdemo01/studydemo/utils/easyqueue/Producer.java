package com.leonxinu.rabbitmqdemo01.studydemo.utils.easyqueue;

import com.leonxinu.rabbitmqdemo01.studydemo.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author :leon
 * @date : 2022/5/13 21:31
 */
@Slf4j
public class Producer {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message = "hello world";
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        log.info("消息发送成功");
    }
}
