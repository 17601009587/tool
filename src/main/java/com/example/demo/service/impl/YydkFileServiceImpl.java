package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.dao.*;
import com.example.demo.entity.*;
import com.example.demo.service.YydkFileService;
import com.example.demo.util.ExcelUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

/**
 * @description:
 * @author: py
 * @create: 2020-01-10 10:24
 **/
@Service("yydkFileService")
public class YydkFileServiceImpl implements YydkFileService {
    private final Logger log = LoggerFactory.getLogger(YydkFileServiceImpl.class);

    @Autowired
    private YydkDcDao yydkDcDao;
    @Autowired
    private YydkLoanDao yydkLoanDao;
    @Autowired
    private YydkRepaymentDao yydkRepaymentDao;
    @Autowired
    private YydkRepaymentDetailDao yydkRepaymentDetailDao;

    @Override
    public void payment() {
        File file = new File("C:\\Users\\admin\\Desktop\\凭证\\yydk\\12月逾期凭证提取\\还款凭证");
        File[] aa = file.listFiles();
        for (int i = 0; i < aa.length; i++) {
            if (aa[i].isFile()) {
                Workbook wb = ExcelUtil.createWorkBook(aa[i].getAbsolutePath());
                YydkRepaymentTotal yydkRepaymentTotal = ExcelUtil.readPaymentContent(wb);
                yydkRepaymentDao.insert(yydkRepaymentTotal.getYydkRepayment());
                for (YydkRepaymentDetail yydkRepaymentDetail : yydkRepaymentTotal.getYydkRepaymentDetails()) {
                    yydkRepaymentDetailDao.insert(yydkRepaymentDetail);
                }
            }
        }

    }

    public static void main(String args[]){
        /*File file = new File("C:\\Users\\admin\\Desktop\\凭证\\yydk\\12月逾期凭证提取\\代偿凭证");
        File[] aa = file.listFiles();
        for (int i = 0; i < aa.length; i++) {
            if (aa[i].isFile()) {
                System.out.println(aa[i].getAbsolutePath());
            }
        }*/
        System.out.println("hello dudu!!!!!!~`");
    }

    @Override
    public void handle() throws IOException {
        YydkDc yydkDc = null;
        File file = new File("C:\\Users\\admin\\Desktop\\凭证\\yydk\\12月逾期凭证提取\\代偿凭证");
        File[] aa = file.listFiles();
        for (int i = 0; i < aa.length; i++) {
            if (aa[i].isFile()) {
                System.out.println(aa[i]);
                String str = readAndWriterTest4(aa[i]);
                yydkDc = new YydkDc();
                dealDcContent(str, yydkDc);
                yydkDcDao.insert(yydkDc);
            }
        }
        String content = null;
        YydkLoan yydkLoan = null;
        File file1 = new File("C:\\Users\\admin\\Desktop\\凭证\\yydk\\12月逾期凭证提取\\放款凭证");
        File[] aa1 = file1.listFiles();
        for (int i = 0; i < aa1.length; i++) {
            if (aa1[i].isFile()) {
                System.out.println(aa1[i]);
                content = readAndWriterTest4(aa1[i]);
                yydkLoan = new YydkLoan();
                dealLoanContent(content, yydkLoan);
                yydkLoanDao.insert(yydkLoan);
            }
        }

    }

    public void dealDcContent(String content, YydkDc yydkDc) {
        content = content.replaceAll("\r|\n", "").replaceAll("\\s*", "");
        yydkDc.setContent(content);
        yydkDc.setBorrowName(content.substring(content.indexOf("借款人：") + 4, content.indexOf("身份证号码")));
        yydkDc.setBorrowIdno(content.substring(content.indexOf("身份证号码：") + 6, content.indexOf("借款金额")));
        yydkDc.setBorrowAmount(new BigDecimal(content.substring(content.indexOf("借款金额：") + 5, content.indexOf("元借款期限"))));
        yydkDc.setBorrowPeriod(content.substring(content.indexOf("借款期限：") + 5, content.indexOf("个月代偿金额")));
        yydkDc.setCompensationAmount(new BigDecimal(content.substring(content.indexOf("代偿金额：") + 5, content.indexOf("元实际还款"))));
        yydkDc.setPaymentAmount(new BigDecimal(content.substring(content.indexOf("追偿金额）：") + 6, content.indexOf("元（"))));
        yydkDc.setOverdueAmount(new BigDecimal(content.substring(content.indexOf("逾期金额：") + 5, content.indexOf("元北"))));
    }

    public void dealLoanContent(String content, YydkLoan yydkLoan) {
        content = content.replaceAll("\r|\n", "").replaceAll("\\s*", "");
        yydkLoan.setContent(content);
        yydkLoan.setLoanTime(content.substring(content.indexOf("放款日期：") + 5, content.indexOf("支付币种")));
        yydkLoan.setLoanAmount(new BigDecimal(content.substring(content.indexOf("金额小写：") + 5, content.indexOf("元金额大写"))));
        yydkLoan.setLoanAmountStr(content.substring(content.indexOf("金额大写：") + 5, content.indexOf("放款户名")));
        yydkLoan.setLoanName(content.substring(content.indexOf("放款户名：") + 5, content.indexOf("放款账号")));
        yydkLoan.setLoanAccount(content.substring(content.indexOf("放款账号：") + 5, content.indexOf("放款银行")));
        yydkLoan.setLoanBank(content.substring(content.indexOf("放款银行：") + 5, content.indexOf("收款户名")));
        yydkLoan.setBorrowName(content.substring(content.indexOf("收款户名：") + 5, content.indexOf("收款账号")));
        yydkLoan.setBorrowAccount(content.substring(content.indexOf("收款账号：") + 5, content.indexOf("收款开户行")));
        yydkLoan.setBorrowBank(content.substring(content.indexOf("收款开户行：") + 6, content.indexOf("用途")));

    }

    public static String readAndWriterTest4(File file) throws IOException {
        String str = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument xdoc = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            fis.close();
            return extractor.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    @Autowired
    private LoanDetailTestDao loanDetailTestDao;

    @Override
    public String test() {
        //查询的
        QueryWrapper<LoanDetailTest> queryWrapper = new QueryWrapper<>();
        //修改的
        UpdateWrapper<LoanDetailTest> objectUpdateWrapper = new UpdateWrapper<>();
        queryWrapper.eq("name","001曹娜").or().eq("name","002曹宗乾");
        List<LoanDetailTest> loanDetailTests = loanDetailTestDao.selectList(queryWrapper);
        loanDetailTestDao.selectOne(queryWrapper);
        return JSON.toJSONString(loanDetailTests);
    }


}
