package com.example.demo.entity;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @description:
 * @author: py
 * @create: 2020-01-10 13:44
 **/
public class YydkRepaymentTotal {
    private YydkRepayment yydkRepayment;
    private List<YydkRepaymentDetail> yydkRepaymentDetails;

    public YydkRepayment getYydkRepayment() {
        return yydkRepayment;
    }

    public void setYydkRepayment(YydkRepayment yydkRepayment) {
        this.yydkRepayment = yydkRepayment;
    }

    public List<YydkRepaymentDetail> getYydkRepaymentDetails() {
        return yydkRepaymentDetails;
    }

    public void setYydkRepaymentDetails(List<YydkRepaymentDetail> yydkRepaymentDetails) {
        this.yydkRepaymentDetails = yydkRepaymentDetails;
    }
}
