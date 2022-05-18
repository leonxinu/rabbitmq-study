package com.leonxinu.rabbitmqdemo01.studydemo.utils.fanoutexchange;

import com.leonxinu.rabbitmqdemo01.studydemo.utils.RabbitmqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author :leon
 * @date : 2022/5/16 22:44
 */
public class ReceiveLogs02 {
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        // 声明交换机 模式为 fanout
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // 临时队列
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName,EXCHANGE_NAME,"");
        System.out.println("等待接收消息。。。。");

        // 接收消息
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            byte[] body = message.getBody();
            String messageBody = new String(body);
            System.out.println("ReceiveLogs02接收到的消息：" + messageBody);
        };
        channel.basicConsume(queueName,true,deliverCallback, (CancelCallback) null);
    }
}
