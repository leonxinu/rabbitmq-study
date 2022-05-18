package com.leonxinu.rabbitmqdemo01.studydemo.utils.workqueue;

import com.leonxinu.rabbitmqdemo01.studydemo.utils.RabbitmqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author :leon
 * @date : 2022/5/13 22:40
 */
public class Worker01 {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            byte[] body = message.getBody();
            String messageBody = new String(body);
            System.out.println("接收到的消息是：" + messageBody);
        };

        CancelCallback callback = consumerTag -> {
            System.out.println("消费者取消消息的回调：" + consumerTag);
        };

        /*1.消费哪个队列
        * 2.消费成功之后是否需要自动应答
        * 3.消费者未消费成功的回调
        * 4.消费者获取消费的回调
        * */
        System.out.println("C2等待接收消息...");
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, callback);
    }
}
