package com.example.demo.choice;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.*;
import com.google.common.io.Files;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * @description: 用枚举代替if-else
 *
 * @author: py
 *
 * @create: 2020-05-01 22:09
 **/
@Builder
@Slf4j
public class ReplaceIfElse {
    final static String JACK = "JACK";

    public static void main(String[] args) {
        optionHelper();
        //getRe("jack");
        SetHelper();
//        MapHelper();
    }

    public static void optionHelper() {
        String userID = "jack1";
        String s = Arrays.stream(Flow.values()).filter(x -> StringUtils.isNotEmpty(userID) && userID.trim().equals(x.getTag())).findAny().map(
                t -> t.handle(userID)).orElseGet(() -> "999999");
        System.out.println(s);
        System.out.println("***********");
        EnumSet<Flow> enumSet = EnumSet.allOf(Flow.class);
        enumSet.stream().filter(x -> x.name().equals(JACK)).map(x -> x.getTag()).forEach(System.out::println);
        System.out.println(enumSet);
        System.out.println("***********");
        List<Flow> collect = Arrays.stream(Flow.values()).map(x -> {
            return x != null ? testop(x, false) : null;
        }).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        System.out.println(collect);
        String str = " - ";
        List<String> list = Splitter.on("-").omitEmptyStrings().trimResults().splitToList(str);
        HashSet<String> strings = Sets.newHashSet(list);
        System.out.println(strings);
    }

    public static Optional<Flow> testop(Flow f, boolean flag) {
        return flag ? Optional.of(f) : Optional.empty();
    }

    private static void other() {
        Multimap<String, Integer> map = ArrayListMultimap.create();
        map.put("aa", 1);
        map.put("aa", 9);
        map.put("aa", 5);
        System.out.println(Joiner.on(",").join(map.get("aa")));  //[1, 2]
        System.out.println(Joiner.on("-").join(Lists.newArrayList("qq", "dd", "ff")));
        System.out.println(Joiner.on(",").withKeyValueSeparator("=").join(ImmutableMap.of(1, 2, 3, 4)));  //[1, 2]
        String str = "1-2-3-4-  5-  6   ";
        List<String> list = Splitter.on("-").omitEmptyStrings().trimResults().splitToList(str);
        HashSet<String> strings = Sets.newHashSet(list);
        System.out.println(list);
        String str1 = "xiaoming=11,xiaohong=23";
        Map<String, String> map1 = Splitter.on(",").withKeyValueSeparator("=").split(str1);
        // 判断匹配结果q
        boolean result = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z')).matches('K'); //true
        // 保留数字文本  CharMatcher.digit() 已过时   retain 保留
        String s1 = CharMatcher.inRange('0', '9').retainFrom("abc 123 efg"); // 123
        // 删除数字文本  remove 删除
        String s2 = CharMatcher.inRange('0', '9').removeFrom("abc 123 efg"); // abc  efg
    }

    private static void getRe(String name) {
        System.out.println(ReplaceIfElse.builder().build().getName("  jack  "));
    }

    public static void MapHelper() {
        HashMap<String, Integer> mapA = Maps.newHashMap();
        mapA.put("a", 1);
        mapA.put("b", 2);
        mapA.put("c", 3);
        boolean aa = mapA.entrySet().stream().anyMatch(x -> x.getKey().equals("aa"));
        HashMap<String, Integer> mapB = Maps.newHashMap();
        mapB.put("b", 20);
        mapB.put("c", 3);
        mapB.put("d", 4);
        mapB.put("e", 5);
        MapDifference<String, Integer> differenceMap = Maps.difference(mapA, mapB);
        Map<String, MapDifference.ValueDifference<Integer>> entriesDiffering = differenceMap.entriesDiffering();
        Map<String, Integer> entriesOnlyLeft = differenceMap.entriesOnlyOnLeft();
        Map<String, Integer> entriesOnlyRight = differenceMap.entriesOnlyOnRight();
        Map<String, Integer> entriesInCommon = differenceMap.entriesInCommon();
        entriesDiffering.forEach((k, v) -> System.out.println(v.leftValue()));   // (2, 20)
        System.out.println(entriesDiffering);    // {b=(2, 20)}
        System.out.println(entriesOnlyLeft);    // {a=1}
        System.out.println(entriesOnlyRight);   // {d=4,e=5}
        System.out.println(entriesInCommon);    // {c=3}
    }

    public static void SetHelper() {
        HashSet setA = Sets.newHashSet(1, 2, 3, 5);
        HashSet setB = Sets.newHashSet(1, 2, 3,6);
        //union 并集:12345867
        Sets.SetView union = Sets.union(setA, setB);
        System.out.println("union:"+union);
        Sets.SetView difference = Sets.symmetricDifference(setB, setA);
        System.out.println("difference:" + difference);        //difference 差集:123
        Sets.SetView intersection = Sets.intersection(setA, setB);
        System.out.println("intersection:" + intersection);  //intersection 交集:45
    }

    public void CacheHelper() throws ExecutionException {
        LoadingCache<String, String> cahceBuilder = CacheBuilder
                .newBuilder()
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        String strProValue = "hello " + key + "!";
                        return strProValue;
                    }
                });
        System.out.println(cahceBuilder.get("wen")); //hello wen!
        cahceBuilder.put("begin", "code");
        System.out.println(cahceBuilder.get("begin")); //code
        Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(1000).build();
        String resultVal = cache.get("code", new Callable<String>() {
            public String call() {
                String strProValue = "begin " + "code" + "!";
                return strProValue;
            }
        });
        System.out.println("value : " + resultVal); //value : begin code!
    }

    public void FileHelper() {
        File file = new File("test.txt");
        List<String> list = null;
        try {
            list = Files.readLines(file, Charsets.UTF_8);
        } catch (Exception e) {
        }

        /*Files.copy(from,to);  //复制文件
        Files.deleteDirectoryContents(File directory); //删除文件夹下的内容(包括文件与子文件夹)
        Files.deleteRecursively(File file); //删除文件或者文件夹
        Files.move(File from, File to); //移动文件
        URL url = Resources.getResource("abc.xml"); //获取classpath根下的abc.xml文件url

        //或者使用如下的操作
        Files.copy(from,to);  //复制文件
        Path path = Paths.get("C:\\Users\\EX-LIFANGTAO001\\IdeaProjects\\Test001\\spring-boot-starter-hello\\src\\main\\resources\\abc\\123\\78");

        MoreFiles.deleteDirectoryContents(path, RecursiveDeleteOption.ALLOW_INSECURE); //删除文件夹下的内容(包括文件与子文件夹)
        MoreFiles.deleteRecursively(File file); //删除文件或者文件夹
        MoreFiles.createParentDirectories(path);  //创建父目录*/
    }

    protected String getName(String userID) {
        return Arrays.stream(Flow.values()).filter(x -> StringUtils.isNotEmpty(userID) && userID.trim().equals(x.getTag())).findFirst().map(
                t -> t.handle(userID)).orElseGet(() -> "no operate");
    }

    //定义枚举类
    enum Flow {
        //给枚举自定义方法
        JACK("jack") {
            @Override
            public String handle(String userID) {
                return "jack";
            }
        },
        MIKE("make") {
            public String handle(String userID) {
                return "mike";
            }

        },
        LILI("lili") {
            @Override
            public String handle(String userID) {
                return "lili";
            }

        };
        //枚举属性
        private final String tag;

        Flow(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return tag;
        }

        public abstract String handle(String userID);
    }
}
