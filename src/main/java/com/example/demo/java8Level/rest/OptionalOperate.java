package com.example.demo.java8Level.rest;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @description: 1.8optional用法
 *
 * @author: py
 *
 * @create: 2020-05-01 21:35
 **/
public class OptionalOperate {
    static Map<Integer, String> map;
    static List<String> list;

    public void Map(int k, String v) {
        Map<Integer, String> integerStringMap = Optional.ofNullable(this.map).orElse(Maps.newHashMap());
        integerStringMap.put(k, v);
    }

    public void List(String v) {
        this.list = Optional.ofNullable(this.list).orElse(Lists.newArrayList());
        list.add(v);
    }

    public static void main(String args[]) {
        OptionalOperate optionalOperate = new OptionalOperate();
        optionalOperate.Map(1, "1");
        System.out.println(map);
        optionalOperate.List("zhangsan");
        System.out.println(list);
        Stu stu = null;
        System.out.println(test66(stu));

    }

    public static Stu test66(Stu stu) {
        Optional.ofNullable(stu).map(x -> x.getId()).map(x -> x + 1).orElseGet(() -> 9);
        return stu;

    }

    @Data
    class Stu {
        private int id;
        private String name;
    }
}
