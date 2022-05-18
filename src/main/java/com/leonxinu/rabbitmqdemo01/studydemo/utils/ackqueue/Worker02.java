package com.leonxinu.rabbitmqdemo01.studydemo.utils.ackqueue;

import com.leonxinu.rabbitmqdemo01.studydemo.utils.RabbitmqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author :leon
 * @date : 2022/5/14 0:39
 */
public class Worker02 {
    public static final String ACK_QUEUE = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitmqUtils.getChannel();
        System.out.println("C2等待接收消息的处理时间较长");


        DeliverCallback deliverCallback = (consumerTag, message) -> {
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("接收到的消息是：" + new String(message.getBody()));
            /*
             * 消息的标记tag
             * 不进行批量应答消息
             * */
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };

        CancelCallback callback = consumerTag -> System.out.println("消费者取消消费的回调：" + consumerTag);
        // 设置不公平分发
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);
        channel.basicConsume(ACK_QUEUE, false, deliverCallback, callback);
    }
}
