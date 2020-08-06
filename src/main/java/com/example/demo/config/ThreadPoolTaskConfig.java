package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: 配置类
 *
 * @author: py
 *
 * @create: 2020-04-25 21:09
 **/
@Configuration
@EnableAsync
public class ThreadPoolTaskConfig {
    /**
     * 默认情况下，在创建了线程池后，线程池中的线程数为0，当有任务来之后，就会创建一个线程去执行任务，
     * 当线程池中的线程数目达到corePoolSize后，就会把到达的任务放到缓存队列当中；
     * 当队列满了，就继续创建线程，当线程数量大于等于maxPoolSize后，开始使用拒绝策略拒绝

     */

    /** 核心线程数（默认线程数） */

    private static final int corePoolSize = 20;

    /** 最大线程数 */

    private static final int maxPoolSize = 100;

    /** 允许线程空闲时间（单位：默认为秒） */

    private static final int keepAliveTime = 10;

    /** 缓冲队列大小 */

    private static final int queueCapacity = 200;

    /** 线程池名前缀 */

    private static final String threadNamePrefix = "Async-Service-";

    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(corePoolSize);
        pool.setMaxPoolSize(maxPoolSize);
        pool.setKeepAliveSeconds(keepAliveTime);
        pool.setQueueCapacity(queueCapacity);
        pool.setAllowCoreThreadTimeOut(false);
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        pool.setThreadNamePrefix(threadNamePrefix);
        pool.initialize();
        return pool;
    }


    /*
    如下方式会使@Async失效:

            1、异步方法使用static修饰
            2、异步类没有使用@Component注解（或其他注解）导致spring无法扫描到异步类
            3、异步方法不能与被调用的异步方法在同一个类中
            4、类中需要使用@Autowired或@Resource等注解自动注入，不能自己手动new对象
            5、如果使用SpringBoot框架必须在启动类中增加@EnableAsync注解*/

    @Async("taskExecutor")
    public void sendMessage() {
        System.out.println("异步发送消息");
    }
}
