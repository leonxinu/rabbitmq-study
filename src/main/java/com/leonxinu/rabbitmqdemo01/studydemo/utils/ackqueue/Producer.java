package com.leonxinu.rabbitmqdemo01.studydemo.utils.ackqueue;

import com.leonxinu.rabbitmqdemo01.studydemo.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @author :leon
 * @date : 2022/5/13 23:31
 * aa bb bb挂掉 worker01会执行bb
 */
public class Producer {
    public static final String ACK_QUEUE = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        // 开启发布确认
        channel.confirmSelect();
        // 需要 queue 队列持久化
        boolean durable = true;
        channel.queueDeclare(ACK_QUEUE,durable,false,false,null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.nextLine();
            // 第三个参数是消息的持久化
            channel.basicPublish("",ACK_QUEUE, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            System.out.println("publisher send message = " + message);
        }
    }
}
