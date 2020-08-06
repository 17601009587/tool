package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.dao.*;
import com.example.demo.entity.LoanDetail;
import com.example.demo.entity.LoanDetailName;
import com.example.demo.entity.TransferDetail;
import com.example.demo.service.CheckDataService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: py
 * @create: 2020-01-06 10:14
 **/
@Service("checkDataService")
public class CheckDataServiceImpl implements CheckDataService {
    private final Logger log= LoggerFactory.getLogger(CheckDataServiceImpl.class);
    @Autowired
    private LoanDetailDao loanDetailDao;

    @Autowired
    private LoanDetailNameDao loanDetailNameDao;
    @Autowired
    private TransferDetailDao transferDetailDao;

    @Autowired
    private TransferDetailNameDao transferDetailNameDao;
    @Autowired
    private CheckDataDao checkDataDao;

    @Override
    public Map<String, Object> dealLoan() {
        StringBuilder sb = new StringBuilder();
        StringBuilder resb = new StringBuilder();

        sb.append("查询异常数据：");
        List<LoanDetailName> loanDetailNames = loanDetailNameDao.queryList();
        for (LoanDetailName loanDetailName : loanDetailNames) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("pcode",loanDetailName.getCode());
            List<LoanDetail> loanXmjrDetails = checkDataDao.queryLoanXmjrData(map);

            for (LoanDetail loanDetail : loanXmjrDetails) {
                int count=checkDataDao.queryLoanData(loanDetail);
                if(count>1){
                    resb.append(JSON.toJSONString(loanDetailName)+"\r\n");

                }
                if(count<1){
                    sb.append(JSON.toJSONString(loanDetailName)+"\r\n");
                    break;
                }
            }
        }
        log.info("loan异常数据：{}",sb.toString());
        log.info("loan重复异常数据：{}",resb.toString());

        return null;
    }

    @Override
    public Map<String, Object> dealTransfer() {
        StringBuilder sb = new StringBuilder();
        StringBuilder resb = new StringBuilder();
        sb.append("查询异常数据：");
        List<LoanDetailName> loanDetailNames = loanDetailNameDao.queryExsitList();
        for (LoanDetailName loanDetailName : loanDetailNames) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("pcode",loanDetailName.getCode());
            List<TransferDetail> loanXmjrDetails = checkDataDao.queryTranXmjrData(map);
            for (TransferDetail loanDetail : loanXmjrDetails) {
                int count=checkDataDao.queryTranData(loanDetail);
                if(count>1){
                    resb.append(JSON.toJSONString(loanDetailName)+"\r\n");
                }
                if(count<1){
                    sb.append(JSON.toJSONString(loanDetailName)+"\r\n");
                    break;
                }
            }
        }
        log.info("loan异常数据：{}",sb.toString());
        log.info("loan重复异常数据：{}",resb.toString());

        return null;
    }
}
