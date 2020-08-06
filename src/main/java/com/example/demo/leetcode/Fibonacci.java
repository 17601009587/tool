package com.example.demo.leetcode;

import java.util.Arrays;

/**
 * @description:斐波那契数
 *
 * @author: py
 *
 * @create: 2020-03-26 15:15
 **/
public class Fibonacci {

    public static void main(String args[]) {
        //普通循环求解
        int n = 5;
        int[] fibonacciArray = new int[n];
        for (int i = 0; i < n; i++) {
            if (i == 0 || i == 1) {
                fibonacciArray[i] = 1;
            } else {
                fibonacciArray[i] = fibonacciArray[i - 1] + fibonacciArray[i - 2];
            }
        }
        System.out.println(Arrays.toString(fibonacciArray));

    }
}
