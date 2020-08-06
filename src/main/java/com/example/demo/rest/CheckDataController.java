package com.example.demo.rest;

import com.example.demo.service.CheckDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.SchemaOutputResolver;

/**
 * @description:
 * @author: py
 * @create: 2020-01-06 10:11
 **/
@RestController
@RequestMapping("data")
public class CheckDataController {
    @Autowired
    private CheckDataService checkDataService;

    //检查放款凭证数据
    @RequestMapping("loanD")
    @ResponseBody
    public void method1() {
        checkDataService.dealLoan();
    }

    //检查放款凭证数据
    @RequestMapping("transferD")
    @ResponseBody
    public void method2() {
        checkDataService.dealTransfer();
    }

    public static void main(String args[]) {
        System.out.println("sjddmjjemmdmd");
        Integer i = 130;
        Integer j = 130;

    }

}
