package com.leonxinu.rabbitmqdemo01.studydemo.utils.topicexchange;

import com.leonxinu.rabbitmqdemo01.studydemo.utils.RabbitmqUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author :leon
 * @date : 2022/5/17 17:16
 */
@Slf4j
public class Producer {
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitmqUtils.getChannel();
        Map<String, String> bindingKeyMap = new HashMap<>();
        bindingKeyMap.put("quick.orange.rabbit", "被Q1Q2接收");
        bindingKeyMap.put("lazy.leon", "只被Q2接收");
        for (Map.Entry<String, String> bindingKeyEntry : bindingKeyMap.entrySet()) {
            String routingKey = bindingKeyEntry.getKey();
            String message = bindingKeyEntry.getValue();
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            log.info(routingKey+"============="+"生产者发出消息：" + message);
        }
    }
}
