package com.example.rabbit.receive.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Package: com.example.rabbit.receive.controller
 * @Description: <>
 * @Author: MILLA
 * @CreateDate: 2020/1/1 17:13
 * @UpdateUser: MILLA
 * @UpdateDate: 2020/1/1 17:13
 * @UpdateRemark: <>
 * @Version: 1.0
 */
@Slf4j
@Service
public class ContractService {

    @RabbitListener(queues = "orderQueue")
    public void messageConsumer(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        try {
            log.info("5.接收到的数据：message-> {} ,tag-> {}", message, tag);
            log.info("6.生成合同(数据去重，插入合同数据)......");
            log.info("7.告知MQ消息被消费掉(进行下一个步骤的处理)......");
            channel.basicAck(tag, false);
        } catch (Exception e) {
//            产生异常
            channel.basicNack(tag, false, true);//重发
//            channel.basicNack(tag, true, false);//数据不重发，丢弃/死信队列
            e.printStackTrace();
        }
    }
}
