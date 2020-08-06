package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.LoanDetail;
import com.example.demo.entity.TransferDetail;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: py
 * @create: 2020-01-06 11:20
 **/
public interface CheckDataDao extends BaseMapper {

    int queryLoanData(LoanDetail entity);

    List<LoanDetail> queryLoanXmjrData(Map<String, Object> map );

    List<TransferDetail> queryTranXmjrData(Map<String, Object> map);

    int queryTranData(TransferDetail loanDetail);
}
