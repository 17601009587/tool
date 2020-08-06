package com.example.demo.event;

import org.springframework.context.ApplicationEvent;

/**
 * @description:
 *
 * @author: py
 *
 * @create: 2020-03-24 21:47
 **/
public class ApiLogEvent extends ApplicationEvent {

    public ApiLogEvent(Object source) {
        super(source);
    }

}
