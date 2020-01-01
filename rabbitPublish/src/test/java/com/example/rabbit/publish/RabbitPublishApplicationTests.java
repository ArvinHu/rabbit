package com.example.rabbit.publish;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class RabbitPublishApplicationTests {


    @Autowired
    RabbitTemplate template;

    @Test
    void contextLoads() {
        template.convertAndSend("", "orderQueue11", "我是测试");
    }


}
