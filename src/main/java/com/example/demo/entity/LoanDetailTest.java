package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;

/**
 * @description:
 * @author: py
 * @create: 2020-01-03 13:18
 **/
@TableName("t2_loandetail_test")
public class LoanDetailTest {
    private String id;
    //放款日期
    private String loanTime;
    //支付币种
    private String currency;
    //金额小写
    private BigDecimal amount;
    //放款户名
    private String loanName;
    //放款账号
    private String loanAccount;
    //放款银行
    private String loanBank;
    //收款户名
    private String receviName;
    //收款账号
    private String receviAccount;
    //收款开户行
    private String receviBank;
    //用途
    private String purpose;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public String getLoanAccount() {
        return loanAccount;
    }

    public void setLoanAccount(String loanAccount) {
        this.loanAccount = loanAccount;
    }

    public String getLoanBank() {
        return loanBank;
    }

    public void setLoanBank(String loanBank) {
        this.loanBank = loanBank;
    }

    public String getReceviName() {
        return receviName;
    }

    public void setReceviName(String receviName) {
        this.receviName = receviName;
    }

    public String getReceviAccount() {
        return receviAccount;
    }

    public void setReceviAccount(String receviAccount) {
        this.receviAccount = receviAccount;
    }

    public String getReceviBank() {
        return receviBank;
    }

    public void setReceviBank(String receviBank) {
        this.receviBank = receviBank;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
