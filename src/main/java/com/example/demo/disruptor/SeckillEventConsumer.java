package com.example.demo.disruptor;

import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 消费者(秒杀处理器)
 * 创建者 科帮网
 */
@Slf4j
public class SeckillEventConsumer implements EventHandler<SeckillEvent> {
    //private ISeckillService seckillService = (ISeckillService) SpringUtil.getBean("seckillService");

    public void onEvent(SeckillEvent seckillEvent, long seq, boolean bool) throws Exception {
        //seckillService.startSeckil(seckillEvent.getSeckillId(), seckillEvent.getUserId());
        log.info("消费方消费了消息:{}", JSON.toJSONString(seckillEvent));
    }
}
