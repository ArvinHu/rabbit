package com.example.rabbit.publish;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@SpringBootApplication
@RestController
@Slf4j
public class RabbitPublishApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitPublishApplication.class, args);
    }

    @Bean
    public Queue createRabbitQueue() {
        return new Queue("orderQueue");
    }

    @Autowired
    private RabbitTemplate template;

    @GetMapping("message/{exchange}/{routingKey}")
    public boolean sendMessage(@PathVariable String exchange, @PathVariable String routingKey) {
        String message = UUID.randomUUID().toString();
        template.convertAndSend(exchange, routingKey, message);
        log.info("发送数据成功:message:{},exchange:{},routingKey:{}", message, exchange, routingKey);
        return true;
    }
}
