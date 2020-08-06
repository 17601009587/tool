package com.example.demo.service.impl;

import com.example.demo.service.ServiceA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @program: demo
 * @description:
 * @author: py
 * @create: 2019-12-24 15:03
 **/
@Service("serviceA")
public class ServiceAImpl implements ServiceA {
    private Logger log = LoggerFactory.getLogger(ServiceAImpl.class);
    @Override
    public void deal() {
        log.info("ServiceAImpl执行了");
    }
}
