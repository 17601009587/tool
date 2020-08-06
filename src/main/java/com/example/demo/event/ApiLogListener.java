package com.example.demo.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @description:
 *
 * @author: py
 *
 * @create: 2020-03-24 21:49
 **/
@Slf4j
@Component
public class ApiLogListener {

    @Async("taskExecutor")
    @Order(1)
    @EventListener(ApiLogEvent.class)
    public void saveApiLog(ApiLogEvent event) {
        System.out.println("********11111*********");
    }

}
