package com.example.demo.concurrent;

import com.google.common.base.Splitter;

import java.util.Spliterator;
import java.util.stream.Collectors;

/**
 * @description:
 *
 * @author: py
 *
 * @create: 2020-07-30 22:37
 **/
public class Stringutil {
    public static void main(String[] args) {
        String str="613047,155535,680242,690920,608158,701872,1,675818,701539,690862,688224,702024,700376,691681,662658,688339,701536,702512,702706,701880,686911,683947,687889,701620,633969,702462,701455,703301,700266,690724,686350,700135,702514,701823,680433,702454,690637,700197,615063,700933,700685,700684,703467,691162,700702,700342,702147,703273,700609,691647,703289,688128,700945,678727,703457,702731,703502,684938,703428,702511,703124,703304,702690,687035,701209,671982,684847,703489,703509,703522,669944,615009,660640,662240,690394,690833,703613,650943,660641,687083,662495,690834,703602,700608,645230,650946,675334,681119,684438,688763,700232,701131,701210,701537,633072,523471,58517,649612,26640,592739,80663,583193,584982,127746,54216,585880,59572,59210,477319,552337,282489,75783,594431,617592,648203,653445,649811,652209,587597,653250,655535,657168,667687,647942,658251,658566,659774,660276,644328,69582,663652,663958,647596,672660,676173,679804,667535,633228,661753,607154,650052,671922,688000,684535,701605,184385,6671";
        System.out.println(Splitter.on(",").splitToList(str).stream().map(x -> "\"" + x + "\"").collect(Collectors.toList()));
    }
}