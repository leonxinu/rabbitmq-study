package com.leonxinu.rabbitmqdemo01.studydemo.utils.article;

import com.leonxinu.rabbitmqdemo01.studydemo.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author :leon
 * @date : 2022/5/16 16:24
 */
public class Producer {

    public static final String message = "hello-message";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        String exchangeName = "hello-exchange";
        channel.exchangeDeclare(exchangeName,"direct",true);
        String routeKey = "hello-routeKey";
        byte[] messageBody = message.getBytes();
        channel.basicPublish(exchangeName,routeKey,null,messageBody);
        channel.close();
    }
}
