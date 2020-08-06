package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @description:
 * @author: py
 * @create: 2020-01-06 10:21
 **/
@TableName("t2_transferdetail_name")
public class TransferDetailName {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
