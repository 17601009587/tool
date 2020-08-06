package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;
@TableName("t2_yydk_repayment")
public class YydkRepayment {

    private String name;

    private String contractNo;

    private String account;

    private BigDecimal amount;

    private String payment;

    private String loanTime;

    private String offTime;

    private Integer period;

    private Integer cunt;

    private String lncfno;

    private BigDecimal totalAmount;

    private BigDecimal totalPrincipal;

    private BigDecimal totalInterestFee;

    private BigDecimal totalDeduction;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment == null ? null : payment.trim();
    }

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime;
    }

    public String getOffTime() {
        return offTime;
    }

    public void setOffTime(String offTime) {
        this.offTime = offTime;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getCunt() {
        return cunt;
    }

    public void setCunt(Integer cunt) {
        this.cunt = cunt;
    }

    public String getLncfno() {
        return lncfno;
    }

    public void setLncfno(String lncfno) {
        this.lncfno = lncfno == null ? null : lncfno.trim();
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalPrincipal() {
        return totalPrincipal;
    }

    public void setTotalPrincipal(BigDecimal totalPrincipal) {
        this.totalPrincipal = totalPrincipal;
    }

    public BigDecimal getTotalInterestFee() {
        return totalInterestFee;
    }

    public void setTotalInterestFee(BigDecimal totalInterestFee) {
        this.totalInterestFee = totalInterestFee;
    }

    public BigDecimal getTotalDeduction() {
        return totalDeduction;
    }

    public void setTotalDeduction(BigDecimal totalDeduction) {
        this.totalDeduction = totalDeduction;
    }
}