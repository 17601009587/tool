package com.example.demo.concurrent;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class FormateTest {

    public static void main(String[] args) {
        String aa="663012,689436,689460,703445,701538,683001,683166,703845,683002,687779,702351,662992,704029,663041,703452,540053,704270,703767,703825,701643,640242,688301,65927,703451,703734,679425,704132,704265,680657,704124,703843,692196,701773,701187,642849,701252,700932,42713,684811,683734,259335,683131,704264,704022,475601,703955,683019,652536,702027,691502";
        System.out.println(Splitter.on(",").splitToList(aa).stream().map(x->"\""+x+"\"").collect(Collectors.joining(",")));
        Double oldPrice = 0d ;
        HashMap<Object, String> objectObjectHashMap = Maps.newHashMap();
        oldPrice= Optional.ofNullable(objectObjectHashMap.get("price5")).map(x->Double.valueOf(x)).orElse(oldPrice);
        System.out.println("*****"+oldPrice);

        System.out.println("666");
        System.out.println(1);

        Map<String, List<Integer>> rootNodeExsit =Maps.newHashMap();
        rootNodeExsit.forEach((k,v)->{
            if (v.contains(1)) {
                System.out.println(k);
            }
        });

        System.out.println(LocalTime.now()+":"+ LocalDate.now());

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        System.out.println(cal.getTime());
        System.out.println(new Date());

    }
}
