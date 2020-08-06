package com.example.demo.helper;

import com.example.demo.event.ApiLogEvent;
import com.example.demo.util.SpringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: demo
 * @description: 切面
 * @author: py
 * @create: 2019-12-11 09:59
 **/
@Component
@Aspect
public class MyAspect {
    private Logger log = LoggerFactory.getLogger(MyAspect.class);

    @Around("execution(* com.example.demo.rest..*.*(..))")
    public Object around(ProceedingJoinPoint point) {
        log.info("进入aop");
        Object o = null;
        try {
            o = point.proceed();
            SpringUtil.publishEvent(new ApiLogEvent("eventPush"));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return o;

    }

}
