package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.dao.LoanDetailDao;
import com.example.demo.dao.LoanDetailNameDao;
import com.example.demo.entity.Expot;
import com.example.demo.entity.LoanDetail;
import com.example.demo.entity.LoanDetailName;
import com.example.demo.service.LoanDetailService;
import com.example.demo.util.ExcelUtil;
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
 * @create: 2020-01-03 13:10
 **/
@Service("loanDetailService")
public class LoanDetailServiceImpl implements LoanDetailService {
    private Logger log = LoggerFactory.getLogger(LoanDetailServiceImpl.class);
    @Autowired
    private LoanDetailDao loanDetailDao;
    @Autowired
    private LoanDetailNameDao loanDetailNameDao;

    @Override
    public void updateName() {
        List<LoanDetailName> loanDetailNames = loanDetailNameDao.selectList(null);
        for (LoanDetailName loanDetailName : loanDetailNames) {
            loanDetailName.setName(String.format("%03d", loanDetailName.getNo())+loanDetailName.getName());
            loanDetailNameDao.updateById(loanDetailName);
        }
    }

    @Override
    public void exportExcel(HttpServletResponse response) {
        List<Map> mapList = Lists.newArrayList();
        String[] headers={"放款日期","支付币种","金额(元)","放款户名","放款账号","放款银行","收款户名","收款账号","收款开户行","用途"};
        Map<String, Object> map=null;
        List<LoanDetailName> loanDetailNames = loanDetailNameDao.queryList();
        for (LoanDetailName loanDetailName : loanDetailNames) {
            map = Maps.newHashMap();
            map.put("name",loanDetailName.getName());
            List<LoanDetail> loanDetails = loanDetailDao.selectByMap(map);
            map.put("fileName",String.format("%03d", loanDetailName.getNo())+loanDetailName.getName());
            map.put("headers",headers);
            map.put("dataList",loanDetails);
            map.put("dataList",loanDetails);
            map.put("no",String.format("%03d", loanDetailName.getNo()));
            mapList.add(map);
        }
        ExcelUtil.exportMultisheetExcel("放款凭证",mapList,response);
    }
    public static void main(String args[]){
        int newNum=3;
        System.out.println(String.format("%03d", newNum));
    }

    @Override
    public void deal() {
        LoanDetail loanDetail = null;
        String filePath = "E:\\xmjr明细\\12月小马平台凭证提取明细802笔 - 放款凭证.xlsx";
        List<Map<String, String>> list = Lists.newArrayList();
        ExecutorService exec = Executors.newFixedThreadPool(8);
        Workbook wb = ExcelUtil.createWorkBook(filePath);
        ArrayList<Future<List<Map<String, String>>>> results = new ArrayList<Future<List<Map<String, String>>>>();
        for (int i = 1; i < 803; i++) {
            int finalI = i;
            Future<List<Map<String, String>>> submit = exec.submit(new Callable<List<Map<String, String>>>() {
                @Override
                public List<Map<String, String>> call() throws Exception {
                    return ExcelUtil.readExcelContent(wb, finalI);
                }
            });
            results.add(submit);
        }
        try {
            for (Future<List<Map<String, String>>> result : results) {
                List<Map<String, String>> maps = result.get();
                list.addAll(maps);
                for (Map<String, String> map : maps) {
                    loanDetail = new LoanDetail();
                    loanDetail.setAmount(new BigDecimal(MapUtils.getString(map, "amount")));
                    loanDetail.setCurrency(MapUtils.getString(map, "currency"));
                    loanDetail.setLoanAccount(MapUtils.getString(map, "loan_account"));
                    loanDetail.setLoanBank(MapUtils.getString(map, "loan_bank"));
                    loanDetail.setLoanName(MapUtils.getString(map, "loan_name"));
                    loanDetail.setLoanTime(MapUtils.getString(map, "loan_time"));
                    loanDetail.setPurpose(MapUtils.getString(map, "purpose"));
                    loanDetail.setReceviAccount(MapUtils.getString(map, "recevi_account"));
                    loanDetail.setReceviBank(MapUtils.getString(map, "recevi_bank"));
                    loanDetail.setReceviName(MapUtils.getString(map, "recevi_name"));
                    loanDetail.setName(MapUtils.getString(map, "name"));
                    loanDetailDao.insert(loanDetail);
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
}
