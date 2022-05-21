package com.leonxinu.rabbitmqdemo01.studydemo.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author :leon
 * @date : 2022/5/13 21:23
 */
public class RabbitmqUtils {
    public static Channel getChannel() throws IOException, TimeoutException {
        /**
         * 提取工具类 其实是为了测试 git
         */
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("118.31.171.156");
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        return channel;
    }
}
