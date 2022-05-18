package com.leonxinu.rabbitmqdemo01.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author :leon
 * @date : 2022/5/18 18:50
 */
@Slf4j
@Component
public class DelayQueueConsumer {
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    @RabbitListener(queues = DELAYED_QUEUE_NAME)
    public void receiveDelayQueue(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间:{},收到延迟队列的消息:{}", new Date(),msg);
    }
}
