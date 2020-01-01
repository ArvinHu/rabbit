package com.example.rabbit.receive;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class RabbitReceiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitReceiveApplication.class, args);
    }

}
