package com.example.demo.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @description: fork-join框架
 * fork : 递归划分子任务,无需合并子任务结果;
 * fork & join : 递归划分子任务,最后合并子任务计算结果;
 *
 * @author: py
 *
 * @create: 2020-03-23 15:45
 **/
public class CountTaskOfForkJoin extends RecursiveTask<Integer> {

    private static final int THREAD_HOLD = 2;
    static ForkJoinPool pool = new ForkJoinPool();
    private int start;
    private int end;

    public CountTaskOfForkJoin(int start, int end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        //如果任务足够小就计算
        boolean canCompute = (end - start) <= THREAD_HOLD;
        if(canCompute){
            for(int i=start;i<=end;i++){
                sum += i;
            }
        }else{
            int middle = (start + end) / 2;
            CountTaskOfForkJoin left = new CountTaskOfForkJoin(start,middle);
            CountTaskOfForkJoin right = new CountTaskOfForkJoin(middle+1,end);
            //执行子任务
            left.fork();
            right.fork();
            //获取子任务结果
            int lResult = left.join();
            int rResult = right.join();
            sum = lResult + rResult;
        }
        return sum;
    }

    public static void main(String[] args){

        CountTaskOfForkJoin task = new CountTaskOfForkJoin(1,10000);
        Future<Integer> result = pool.submit(task);
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
