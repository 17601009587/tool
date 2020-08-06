package com.example.demo.concurrent;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * @description: 交替打印英文字母
 *
 * @author: py
 *
 * @create: 2020-04-10 18:54
 **/
public class AlternatePrint {
    enum ReadytoRun{t1,t2};
    private static volatile  ReadytoRun r = ReadytoRun.t1;

    static Thread t1 = null ,t2 = null ;

    public static void main(String args[]) {
        printOfVolatile();
        printOfVolatile2();
    }

    private static void printOfVolatile2() {
        List<Object> result = Lists.newCopyOnWriteArrayList();
        char[] letters1 = "abcdefg".toCharArray();
        char[] letters2 = "1234567".toCharArray();
        t1 = new Thread(() -> {
            for (char c : letters1) {
                while (r!=ReadytoRun.t1){}
                result.add(c);
                r=ReadytoRun.t2;
            }
        },"t1");

        t2 = new Thread(()->{
            for (char c : letters2) {
                while (r!=ReadytoRun.t2){}
                result.add(c);
                r=ReadytoRun.t1;

            }
        },"t2");
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

    private static void printOfVolatile() {
        List<Object> result = Lists.newArrayList();
        char[] letters1 = "abcdefg".toCharArray();
        char[] letters2 = "1234567".toCharArray();
        t1 = new Thread(() -> {
            for (char c : letters1) {
                result.add(c);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        },"t1");

        t2 = new Thread(()->{
            for (char c : letters2) {
                result.add(c);
                LockSupport.park();
                LockSupport.unpark(t1);

            }
        },"t2");
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(result);

    }
}
