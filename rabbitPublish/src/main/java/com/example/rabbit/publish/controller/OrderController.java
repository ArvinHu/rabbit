package com.example.rabbit.publish.controller;

import com.example.rabbit.publish.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package: com.example.rabbit.publish.controller
 * @Description: <>
 * @Author: MILLA
 * @CreateDate: 2020/1/1 16:53
 * @UpdateUser: MILLA
 * @UpdateDate: 2020/1/1 16:53
 * @UpdateRemark: <>
 * @Version: 1.0
 */
@RestController
@RequestMapping(value = "order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    @Autowired
    private IOrderService service;

    @GetMapping(value = "saving")
    public int saveOrder() {
        return service.saveOrder();
    }
}
