package com.example.demo.concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * @description: 交替打印英文字母
 *
 * @author: py
 *
 * @create: 2020-04-10 18:54
 **/
public class Print {
    private static volatile boolean flag = true;
    static Thread t1=null,t2=null;

    public static void main(String args[]) {
        printOfVolatile();
        printOfVolatile2();
    }

    private static void printOfVolatile2(){
        char[] result = new char[52];
        t1 = new Thread(() -> {
            for (int i = 0; i < 26; i++) {
                result[i * 2] = (char) ('a' + i);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });
        t2 = new Thread(() -> {
            for (int i = 0; i < 26; i++) {
                result[i * 2 + 1] = (char) ('A' + i);
                LockSupport.park();
                LockSupport.unpark(t1);
            }
        });
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
        char[] result = new char[52];
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 26; ) {
                    if (flag) {
                        result[i * 2] = (char) ('a' + i);
                        flag = false;
                        i++;
                    }
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 26; ) {
                    if (!flag) {
                        result[i * 2 + 1] = (char) ('A' + i);
                        flag = true;
                        i++;
                    }
                }
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}
