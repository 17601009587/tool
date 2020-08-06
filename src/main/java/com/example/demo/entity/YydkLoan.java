package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;

/**
 * @description:
 * @author: py
 * @create: 2020-01-10 10:21
 **/
@TableName("t2_yydk_loan")
public class YydkLoan {
    private String content;
    private String loanTime;
    private BigDecimal loanAmount;
    private String loanAmountStr;
    private String loanName;
    private String loanAccount;
    private String loanBank;
    private String borrowName;
    private String borrowAccount;
    private String borrowBank;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanAmountStr() {
        return loanAmountStr;
    }

    public void setLoanAmountStr(String loanAmountStr) {
        this.loanAmountStr = loanAmountStr;
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

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getBorrowAccount() {
        return borrowAccount;
    }

    public void setBorrowAccount(String borrowAccount) {
        this.borrowAccount = borrowAccount;
    }

    public String getBorrowBank() {
        return borrowBank;
    }

    public void setBorrowBank(String borrowBank) {
        this.borrowBank = borrowBank;
    }
}
