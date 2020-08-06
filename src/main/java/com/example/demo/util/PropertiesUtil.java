package com.example.demo.util;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: demo
 * @description:
 * @author: py
 * @create: 2019-12-10 16:27
 **/
@Component
@ConfigurationProperties
@PropertySource("classpath:mapping.properties")
public class PropertiesUtil implements InitializingBean {
    private static Map<String, Object> dbEsMapping;

    public Map<String, Object> getDbEsMapping() {
        return dbEsMapping;
    }

    public void setDbEsMapping(Map<String, Object> dbEsMapping) {
        this.dbEsMapping = dbEsMapping;
    }

    public static Object getValue(String key) {
        return dbEsMapping.get(key);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        dbEsMapping.forEach((x, y) -> {
            System.out.println(x + ":" + y);
        });

    }

    public static void main(String args[]){
        HashMap<Object, Object> objectObjectHashMap = Maps.newHashMap();
        objectObjectHashMap.put("1", "1");
        objectObjectHashMap.put("2", "2");
        objectObjectHashMap.forEach((x, y) -> {
            System.out.println(x + ":" + y);
        });
    }
}
