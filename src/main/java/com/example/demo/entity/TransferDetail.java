package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;

/**
 * @description:
 * @author: py
 * @create: 2020-01-03 13:18
 **/
@TableName("t2_transferdetail_bak")
public class TransferDetail {
    //代偿日期

    private String compensationDate;
    //标的编号
    private String subjectNumber;
    //出借人姓名
    private String lenderName;
    //身份证号
    private String lenderIdNo;
    //手机号
    private String lenderMobieleNo;
    //出借人账户
    private String lenderAccount;
    //出借金额
    private BigDecimal lenderAmount;
    //代偿汇入金额
    private BigDecimal compensationAmount;
    //借款人汇入金额
    private BigDecimal repayAmount;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompensationDate() {
        return compensationDate;
    }

    public void setCompensationDate(String compensationDate) {
        this.compensationDate = compensationDate;
    }

    public String getSubjectNumber() {
        return subjectNumber;
    }

    public void setSubjectNumber(String subjectNumber) {
        this.subjectNumber = subjectNumber;
    }

    public String getLenderName() {
        return lenderName;
    }

    public void setLenderName(String lenderName) {
        this.lenderName = lenderName;
    }

    public String getLenderIdNo() {
        return lenderIdNo;
    }

    public void setLenderIdNo(String lenderIdNo) {
        this.lenderIdNo = lenderIdNo;
    }

    public String getLenderMobieleNo() {
        return lenderMobieleNo;
    }

    public void setLenderMobieleNo(String lenderMobieleNo) {
        this.lenderMobieleNo = lenderMobieleNo;
    }

    public String getLenderAccount() {
        return lenderAccount;
    }

    public void setLenderAccount(String lenderAccount) {
        this.lenderAccount = lenderAccount;
    }

    public BigDecimal getLenderAmount() {
        return lenderAmount;
    }

    public void setLenderAmount(BigDecimal lenderAmount) {
        this.lenderAmount = lenderAmount;
    }

    public BigDecimal getCompensationAmount() {
        return compensationAmount;
    }

    public void setCompensationAmount(BigDecimal compensationAmount) {
        this.compensationAmount = compensationAmount;
    }

    public BigDecimal getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(BigDecimal repayAmount) {
        this.repayAmount = repayAmount;
    }
}
