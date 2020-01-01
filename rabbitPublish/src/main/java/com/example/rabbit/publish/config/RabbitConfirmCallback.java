package com.example.rabbit.publish.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @Package: com.example.rabbit.publish.config
 * @Description: <>
 * @Author: MILLA
 * @CreateDate: 2020/1/1 12:48
 * @UpdateUser: MILLA
 * @UpdateDate: 2020/1/1 12:48
 * @UpdateRemark: <>
 * @Version: 1.0
 */
@Slf4j
@Component
public class RabbitConfirmCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    @Autowired
    private RabbitTemplate template;
    ThreadLocal<Boolean> isReturenCallback = new ThreadLocal<>();

    @PostConstruct
    public void initRabbitTemplate() {
        //设置发布信息确认
        template.setReturnCallback(this);
        template.setConfirmCallback(this);
    }

    //    消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器，也就是只确认是否正确到达 Exchange 中
    @Override
    public void confirm(@Nullable CorrelationData correlationData, boolean ack, @Nullable String cause) {
        //exchange是对的 queue是对的 ack=true
        //exchange是对的 queue是错的 ack=true 只有该种情况会走returnCallback
        //exchange是错的 queue是对的 ack=false
        //exchange是错的 queue是错的 ack=false

        log.info("3.2.ack :[{}],如果是true-->发送成功,cause:{},correlationData:{}", ack, cause, correlationData);
        //如果发送成功修改数据库
        if (ack && Objects.isNull(isReturenCallback.get())) {
            log.info("4.修改推送记录表数据的状态");
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        //只有当exchange是正确的且queue是错误的的时候才会执行
        log.info("3.1.失败回调 message:{}, replyCode: {} ,replyText :{} ,exchange: {} ,routingKey: {}", new String(message.getBody()), replyCode, replyText, exchange, routingKey);
        isReturenCallback.set(true);
    }
}
