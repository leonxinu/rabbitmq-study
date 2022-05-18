package com.leonxinu.rabbitmqdemo01.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author :leon
 * @date : 2022/5/18 0:51
 * 发送延迟消息
 */
@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMsgController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMessage/{message}")
    public void sendMsg(@PathVariable String message) {
        log.info("当前时间:{},发送一条信息给两个ttl队列:{}", new Date(),message);
        rabbitTemplate.convertAndSend("X","XA","消息来自ttl为10s的队列："+message);
        rabbitTemplate.convertAndSend("X","XB","消息来自ttl为40s的队列："+message);
    }

    @GetMapping("/sendMessage/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message,
                        @PathVariable String ttlTime) {
        log.info("当前时间:{},发送一条时长为:{}ms的ttl消息给队列QC:{}", new Date(),ttlTime,message);
        rabbitTemplate.convertAndSend("X","XA",message,msg -> {
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
    }
}
