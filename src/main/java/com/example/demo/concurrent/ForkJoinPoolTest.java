package com.example.demo.concurrent;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: fork
 *          fork : 递归划分子任务,无需合并子任务结果;
 * fork & join : 递归划分子任务,最后合并子任务计算结果;
 * @author: py
 *
 * @create: 2020-05-05 22:01
 **/
@Slf4j
public class ForkJoinPoolTest {
    private final static List<Integer> sender = new ArrayList<Integer>(21000000);
    private final static List<Integer> receiver = new ArrayList<>(21000000);
    private final static List<Integer> receiver2 = new ArrayList<>(21000000);
    private final static AtomicInteger i = new AtomicInteger(0);

    static {
        log.info("prepare data");
        while (i.get() < 21000000) {
            sender.add(i.get());
            i.incrementAndGet();
        }
        log.info("prepare over");
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        SendTask sendTask = new SendTask(0, 210000, sender);
        log.info("Task Start !");
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        forkJoinPool.submit(sendTask);
        forkJoinPool.awaitTermination(10, TimeUnit.SECONDS);
        forkJoinPool.shutdown();
        stopWatch.stop();
        log.info("sender.size -> {}", sender.size());
        log.info("receiver.size -> {}-{}", receiver.size(), Sets.newHashSet(receiver).size());
        log.info("TotalTimeMillis1 -> " + stopWatch.getTotalTimeMillis());

    }

    @Slf4j
    @AllArgsConstructor
    public static class SendTask extends RecursiveTask<Void> {
        //定义递归子任务的阈值
        private final static int preSize = 100;
        private int start;
        private int end;
        private List<Integer> tempList;
        private final static AtomicInteger taskId = new AtomicInteger(0);

        /**
         * The main computation performed by this task.
         */
        @Override
        protected Void compute() {
            if (end - start < preSize) {
                //log.info("add start {} to end {}", start, end);
                for (int i = start; i < end; i++) {
                    add(this.tempList.get(i));
                }
            } else {
                int middle = (start + end) / 2;
                RecursiveTask sendTaskLeft = new SendTask(start, middle, this.tempList);
                RecursiveTask sendTaskRight = new SendTask(middle, end, this.tempList);
                SendTask.invokeAll(sendTaskLeft, sendTaskRight);
            }
            return null;
        }

        //防止并发,因为在第一次没有在add方法做同步限制,导致并发,找个好久问题
        public void add(int i) {
            synchronized (SendTask.class) {
                receiver.add(i);
            }
        }
    }
}
