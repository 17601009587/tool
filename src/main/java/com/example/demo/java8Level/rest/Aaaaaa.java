package com.example.demo.java8Level.rest;

import com.google.common.collect.Lists;

/**
 * @description:
 *
 * @author: py
 *
 * @create: 2020-05-31 20:40
 **/
public class Aaaaaa {
    public static void main(String[] args) {
        Lists.newArrayList(1,2,3).stream()
                .map(x->x+1).filter(x->x==3)
                .forEach(System.out::println);
    }

}
