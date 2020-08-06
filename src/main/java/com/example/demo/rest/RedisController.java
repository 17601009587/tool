package com.example.demo.rest;

import com.example.demo.redis.RedisUtil;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: demo
 * @description: 缓存测试控制器
 * @author: py
 * @create: 2020-01-02 14:16
 **/
@RestController
@RequestMapping("redis")
public class RedisController {
    private Logger log = LoggerFactory.getLogger(RedisController.class);
    private String KEY="yy";
    private String HASH_KEY="hash_yy";
    private String COUNT_KEY="count_yy";
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("m1")
    public void method(){
        redisUtil.set(KEY, 1234568910, 10);
        log.info("k-v:{}",redisUtil.get(KEY));
        redisUtil.hset(HASH_KEY,"name","张三",10000);
        log.info("k-v:{}",redisUtil.hget(HASH_KEY,"name"));
        Map<String, Object> map = Maps.newHashMap();
        map.put("addr","shanxi");
        map.put("sex",3.3);
        redisUtil.hmset(HASH_KEY, map, 10000);
        log.info("k-v:{}",redisUtil.hmget(HASH_KEY));
    }


}
