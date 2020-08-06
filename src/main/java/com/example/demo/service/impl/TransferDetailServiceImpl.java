package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.dao.LoanDetailNameDao;
import com.example.demo.dao.TransferDetailDao;
import com.example.demo.dao.TransferDetailTotalDao;
import com.example.demo.entity.LoanDetail;
import com.example.demo.entity.LoanDetailName;
import com.example.demo.entity.TransferDetail;
import com.example.demo.entity.TransferDetailTotal;
import com.example.demo.service.TransferDetailService;
import com.example.demo.util.ExcelUtil;
import com.example.demo.util.MergeExcelUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.MapUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @description:
 * @author: py
 * @create: 2020-01-03 13:11
 **/
@Service("transferDetailService")
public class TransferDetailServiceImpl implements TransferDetailService {
    private Logger log = LoggerFactory.getLogger(TransferDetailServiceImpl.class);
    @Autowired
    private TransferDetailDao transferDetailDao;

    @Autowired
    private LoanDetailNameDao loanDetailNameDao;
    @Autowired
    private TransferDetailTotalDao transferDetailTotalDao;

    @Override
    public void transferDetailService(HttpServletResponse response) {
        List<Map> mapList = Lists.newArrayList();
        String[] headers = {"序号","代偿日期", "标的编号", "出借人姓名", "身份证号", "手机号", "出借人账户", "出借金额", "小马汇入金额", "借款人汇入金额"};
        Map<String, Object> map = null;
        List<TransferDetailTotal> transferDetailTotals=transferDetailTotalDao.queryTranList();
        //List<LoanDetailName> loanDetailNames = loanDetailNameDao.queryList();
        List<String> errorList = Lists.newArrayList();
        for (TransferDetailTotal loanDetailName : transferDetailTotals) {
            map = Maps.newHashMap();
            map.put("name", loanDetailName.getName());
            List<TransferDetail> entitys = transferDetailDao.selectByMap(map);
            if(entitys==null){
                errorList.add(loanDetailName.getName());
            }
            map.put("fileName", String.format("%03d", loanDetailName.getId())+loanDetailName.getName());
            map.put("headers", headers);
            map.put("dataList", entitys);
            map.put("row",loanDetailName);
            map.put("no",String.format("%03d", loanDetailName.getId()));
            mapList.add(map);
        }
        ExcelUtil.exportMultisheetTranExcel("资金划转证明", mapList, response);
        log.info("异常数据集合：{}",JSON.toJSONString(errorList));
    }

    @Override
    public void deal() {
        TransferDetail transferDetail = null;
        String filePath = "E:\\xmjr明细\\12月小马平台凭证提取明细802笔-资金划转证明.xlsx";
        List<Map<String, String>> list = Lists.newArrayList();
        ExecutorService exec = Executors.newFixedThreadPool(8);
        Workbook wb = ExcelUtil.createWorkBook(filePath);
        ArrayList<Future<List<Map<String, String>>>> results = new ArrayList<Future<List<Map<String, String>>>>();
        for (int i = 675; i < 803; i++) {
            int finalI = i;
            Future<List<Map<String, String>>> submit = exec.submit(new Callable<List<Map<String, String>>>() {
                @Override
                public List<Map<String, String>> call() throws Exception {
                    return ExcelUtil.readExcelTransferContent(wb, finalI);
                }
            });
            results.add(submit);
        }
        try {
            for (Future<List<Map<String, String>>> result : results) {
                List<Map<String, String>> maps = result.get();
                list.addAll(maps);
                for (Map<String, String> map : maps) {
                    transferDetail = new TransferDetail();
                    transferDetail.setCompensationDate(MapUtils.getString(map, "compensation_date"));
                    transferDetail.setSubjectNumber(MapUtils.getString(map, "subject_number"));
                    transferDetail.setLenderName(MapUtils.getString(map, "lender_name"));
                    transferDetail.setLenderIdNo(MapUtils.getString(map, "lender_idNo"));
                    transferDetail.setLenderMobieleNo(MapUtils.getString(map, "lender_mobieleNo"));
                    transferDetail.setLenderAccount(MapUtils.getString(map, "lender_account"));
                    transferDetail.setLenderAmount(new BigDecimal(MapUtils.getString(map, "lender_amount")));
                    transferDetail.setCompensationAmount(new BigDecimal(MapUtils.getString(map, "compensation_amount")));
                    transferDetail.setRepayAmount(new BigDecimal(MapUtils.getString(map, "repay_amount")));
                    transferDetail.setName(MapUtils.getString(map, "name"));
                    transferDetailDao.insert(transferDetail);
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            exec.shutdown();
        }
        log.info(JSON.toJSONString(list));
    }

    @Override
    public void dealTotal() {
        TransferDetailTotal transferDetail = null;
        String filePath = "E:\\xmjr明细\\12月小马平台凭证提取明细802笔-资金划转证明.xlsx";
        List<Map<String, String>> list = Lists.newArrayList();
        ExecutorService exec = Executors.newFixedThreadPool(8);
        Workbook wb = ExcelUtil.createWorkBook(filePath);
        for (int i = 1; i < 803; i++) {
            Map<String, String> map = MergeExcelUtil.readExcelTransferContent(wb, i);
            String content = MapUtils.getString(map, "content").replaceAll("\r|\n", "").replaceAll("\\s*", "");
            transferDetail = new TransferDetailTotal();
            transferDetail.setName(MapUtils.getString(map, "name"));
            transferDetail.setContent(content);
            transferDetail.setAccount(content.substring(content.indexOf("账号：")+3,content.indexOf("向出借人如")));
            transferDetail.setAccountName(content.substring(content.indexOf("户名：")+3,content.indexOf(",账号")));
            transferDetail.setAmount(new BigDecimal(content.substring(content.indexOf("总计：")+3,content.indexOf("元（"))));
            transferDetail.setAccountStr(content.substring(content.indexOf("大写：")+3,content.indexOf(")")));
            transferDetailTotalDao.insert(transferDetail);
        }
        try {
            /*for (Future<Map<String, String>> result : results) {
                Map<String, String> map = result.get();
                    String content = MapUtils.getString(map, "content").replaceAll("\r|\n", "").replaceAll("\\s*", "");
                    transferDetail = new TransferDetailTotal();
                    transferDetail.setName(MapUtils.getString(map, "name"));
                    transferDetail.setContent(content);
                    transferDetail.setAccount(content.substring(content.indexOf("账号：")+3,content.indexOf("向出借人如")));
                    transferDetail.setAccountName(content.substring(content.indexOf("户名：")+3,content.indexOf(",账号")));
                    transferDetail.setAmount(new BigDecimal(content.substring(content.indexOf("总计：")+3,content.indexOf("元（"))));
                    transferDetail.setAccountStr(content.substring(content.indexOf("大写：")+3,content.indexOf(")")));
                    transferDetailTotalDao.insert(transferDetail);

            }*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exec.shutdown();
        }
        log.info(JSON.toJSONString(list));
    }

    public static void main(String args[]){
        String content="    北京小马金融信息服务有限公司通过我行发送指令的方式从账户信息：户名：  北京鼎诚和晟信息技术服务有限公司  , \t\t\t\t\t\t\t\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\n" +
                "账号：  8700000011    向出借人如下账户划转人民币总计：\t\t\t\t\t2609.67 \t元（大写：                                                                                   \t贰仟陆佰零玖元陆角柒分)。\t\t\n" +
                "\t\t\t\t\t\t\t\t\t\n";
        content=content.replaceAll("\r|\n", "").replaceAll("\\s*", "");
        String accountName= content.substring(content.indexOf("户名：")+3,content.indexOf(",账号"));
        String account= content.substring(content.indexOf("账号：")+3,content.indexOf("向出借人如"));
        String amount= content.substring(content.indexOf("总计：")+3,content.indexOf("元（"));
        String accountStr= content.substring(content.indexOf("大写：")+3,content.indexOf(")"));
        System.out.println(content);
        System.out.println(account);
        System.out.println(accountName);
        System.out.println(amount);
        System.out.println(accountStr);
    }
}
