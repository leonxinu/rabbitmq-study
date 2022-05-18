package com.leonxinu.rabbitmqdemo01.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author :leon
 * @date : 2022/5/18 1:02
 *
 * 队列 ttl 消费者
 */
@Slf4j
@Component
public class DeadLetterQueueConsumer {

    @RabbitListener(queues = "QD")
    public void receiverQD(Message message, Channel channel){
        byte[] body = message.getBody();
        String messageBody = new String(body);
        log.info("当前时间:{},收到死信队列的消息:{}", new Date(),messageBody);
    }
}
