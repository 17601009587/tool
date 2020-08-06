package com.example.demo.rest.encry;

import com.example.demo.event.ApiLogEvent;
import com.example.demo.util.R;
import com.example.demo.util.SpringUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description: 加解密控制器
 * @author: py
 * @create: 2020-01-17 23:05
 **/
@RestController
@RequestMapping("encry")
public class EncryController {
    Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 响应数据 加密
     */
    @RequestMapping(value = "/sendResponseEncryData")
    public R sendResponseEncryData(String name) {
        SpringUtil.publishEvent(new ApiLogEvent("eventPush"));
        return R.ok(name);

    }

    /**
     * 获取 解密后的 请求参数
     */
    @RequestMapping(value = "/getRequestData")
    public R getRequestData(@RequestBody Object object) {
        log.info("controller接收的参数object={}", object.toString());
        return R.ok();
    }

    private LoadingCache<Long, AtomicLong> counter = CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.SECONDS)
            .build(new CacheLoader<Long, AtomicLong>() {
                @Override
                public AtomicLong load(Long seconds) throws Exception {
                    return new AtomicLong(0);
                }
            });
    public static long permit = 50;

    public R getData() throws ExecutionException {
        //得到当前秒
        long currentSeconds = System.currentTimeMillis() / 1000;
        if (counter.get(currentSeconds).incrementAndGet() > permit) {
            return R.error(404, "访问速率过快");
        }
        return R.ok();

    }
    public static void main(String args[]){
        AnnotationConfigApplicationContext annotationConfigApplicationContext=new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.getBean("");
    }
}

