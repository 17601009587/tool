package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;
@TableName("t2_yydk_repayment_detail")
public class YydkRepaymentDetail {

    private String lncfno;

    private Integer period;

    private String repaymentTime;

    private BigDecimal amount;

    private BigDecimal principal;

    private BigDecimal interestFee;

    private BigDecimal deduction;


    public String getLncfno() {
        return lncfno;
    }

    public void setLncfno(String lncfno) {
        this.lncfno = lncfno == null ? null : lncfno.trim();
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(String repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getInterestFee() {
        return interestFee;
    }

    public void setInterestFee(BigDecimal interestFee) {
        this.interestFee = interestFee;
    }

    public BigDecimal getDeduction() {
        return deduction;
    }

    public void setDeduction(BigDecimal deduction) {
        this.deduction = deduction;
    }
}