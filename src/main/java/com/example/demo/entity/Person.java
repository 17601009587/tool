package com.example.demo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @program: demo
 * @description:
 * @author: py
 * @create: 2019-12-31 11:22
 **/
public class Person {
    private Integer id;
    @Excel(name="姓名",width = 20)
    private String name;
    @Excel(name="年龄",width = 20)
    private Integer age;
    @Excel(name="性别",width = 20,replace = {"男_1","女_0"},suffix = "生")
    private Integer sex;

    public Person(Integer id, String name, Integer age, Integer sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public Person() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
