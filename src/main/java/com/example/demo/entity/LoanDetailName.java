package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @description:
 * @author: py
 * @create: 2020-01-06 10:20
 **/
@TableName("t2_loandetail_total")
public class LoanDetailName {
    private int id;
    private int no;
    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
