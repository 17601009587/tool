package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.LoanDetailName;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @description:
 * @author: py
 * @create: 2020-01-03 13:05
 **/
public interface LoanDetailNameDao extends BaseMapper<LoanDetailName> {
    List<LoanDetailName> queryList();

    List<LoanDetailName> queryExsitList();
}
