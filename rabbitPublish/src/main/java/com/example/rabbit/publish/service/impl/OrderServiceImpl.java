package com.example.rabbit.publish.service.impl;

import com.example.rabbit.publish.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @Package: com.example.rabbit.publish.service.impl
 * @Description: <>
 * @Author: MILLA
 * @CreateDate: 2020/1/1 16:55
 * @UpdateUser: MILLA
 * @UpdateDate: 2020/1/1 16:55
 * @UpdateRemark: <>
 * @Version: 1.0
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private RabbitTemplate template;

    @Override
    public int saveOrder() {
        saveOrderLocal();
        //手动接收推送的结果
        log.info("2.推送订单消息到MQ....");
        String message = UUID.randomUUID().toString();
        template.convertAndSend("", "orderQueue", message);
        log.info("2.1.发送数据成功:message:{}", message);
        //推送成功，会收到成功的回执，然后修改推送记录的数据状态，如果推送失败的话，会通过定时任务进行重新扫描再度进行推送
        log.info("3.回调函数接收MQ的推送结果....");
        return 0;
    }

    @Transactional//生成订单和生成需要推送的记录是同一个事务
    public void saveOrderLocal() {
        log.info("1.1.生成订单....");
        log.info("1.2.生成需要推送记录....");
    }
}
