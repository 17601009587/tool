package com.example.demo.rest;

import com.example.demo.disruptor.DisruptorUtil;
import com.example.demo.disruptor.SeckillEvent;
import com.example.demo.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 *
 * @author: py
 *
 * @create: 2020-03-30 15:49
 **/
@RestController
@RequestMapping("dis")
public class DisruptorTest {
    @Autowired
    RedisTemplate redisTemplate;

    ExecutorService pool = Executors.newFixedThreadPool(10);

    @RequestMapping("producer")
    public void method() {
        int i=0;
        do {
            SeckillEvent kill = new SeckillEvent();
            kill.setSeckillId(666);
            kill.setUserId(888);
            DisruptorUtil.producer(kill);
        }while (i++<1000);
    }

    @RequestMapping("m")
    public R method1() {
        //分布式锁
        redisTemplate.opsForValue().setIfAbsent("key", "v", 10, TimeUnit.SECONDS);
        return R.ok();
    }

}
