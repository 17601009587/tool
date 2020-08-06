package com.example.demo.rest;

import com.example.demo.entity.CloudStorageConfig;
import com.example.demo.service.ServiceA;
import com.example.demo.util.R;
import com.example.demo.util.ValidatorUtils;
import com.example.demo.validate.QiniuGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;

/**
 * @program: demo
 * @description: 日志跟踪控制器
 * @author: py
 * @create: 2019-12-11 10:04
 **/
@Controller
@RequestMapping("/my")
public class MdcController {
    Logger logger = LoggerFactory.getLogger(MdcController.class);
    @Autowired
    private ServiceA serviceA;

    @RequestMapping("/king")
    @ResponseBody
    public R method(String s) throws Exception {
        String traceId = MDC.get("traceId");
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                for (int j = 0; j < 3; j++) {
                    MDC.put("traceId", traceId);
                    serviceA.deal();
                    logger.info(Thread.currentThread().getName());
                    MDC.remove("traceId");
                }
            }).start();
        }
        for (int i = 0; i < 3; i++) {
            serviceA.deal();
            logger.info("外层：" + Thread.currentThread().getName());
        }
        CloudStorageConfig cloudStorageConfig = new CloudStorageConfig();
        cloudStorageConfig.setAliyunAccessKeyId("1");
        cloudStorageConfig.setType(1);
        ValidatorUtils.validateEntity(cloudStorageConfig);
        if(cloudStorageConfig.getType()==1){
            ValidatorUtils.validateEntity(cloudStorageConfig, QiniuGroup.class);
        }
        return R.ok();
    }
    public static void main(String args[]){
        LinkedList linkedList = new LinkedList();
        do {
            byte[] b=new byte[1024*1024];
            linkedList.add(b);
        }while (true);
    }


}
