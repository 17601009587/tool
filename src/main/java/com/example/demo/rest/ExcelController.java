package com.example.demo.rest;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.Expot;
import com.example.demo.service.LoanDetailService;
import com.example.demo.service.TransferDetailService;
import com.example.demo.util.ExcelUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @description:
 * @author: py
 * @create: 2020-01-02 17:09
 **/
@RestController
@RequestMapping("excel")
public class ExcelController {
    private Logger log = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    private LoanDetailService loanDetailService;

    @Autowired
    private TransferDetailService transferDetailService;

    //导出放款凭证
    @RequestMapping("exportLoanExcel")
    @ResponseBody
    public void method(HttpServletResponse response){
        loanDetailService.exportExcel(response);
    }
    //导出资金划转证明
    @RequestMapping("exportTranExcel")
    @ResponseBody
    public void method5(HttpServletResponse response){
        transferDetailService.transferDetailService(response);
    }

    //入库放款凭证
    @RequestMapping("loan")
    @ResponseBody
    public void method1(){
        loanDetailService.deal();
    }

    //入库资金划转证明
    @RequestMapping("transfer")
    @ResponseBody
    public void method2(){
        transferDetailService.deal();
    }

    @RequestMapping("update")
    @ResponseBody
    public void method(){
        loanDetailService.updateName();
    }


    @RequestMapping("exportExcelTest")
    @ResponseBody
    public void method(HttpServletResponse response, HttpServletRequest request){
        List<Map> mapList = Lists.newArrayList();

        String[] headers={"放款日期","支付币种","金额(元)","放款户名","放款账号","放款银行","收款户名","收款账号","收款开户行","用途"};
        List<Expot> dataList = null;
        Expot data =null;
        String fileName="sheet";
        Map<String, Object> map=null;
        for (int i = 0; i < 3; i++) {
            map = Maps.newHashMap();
            dataList = Lists.newArrayList();

            for (int i1 = 0; i1 < 2; i1++) {
                data = new Expot();
                data.setName("zhangsan"+i1);
                data.setName1("zhangsan"+i1);
                data.setName2("zhangsan"+i1);
                data.setName3("zhangsan"+i1);
                data.setName4("zhangsan"+i1);
                data.setName5("zhangsan"+i1);
                data.setName6("zhangsan"+i1);
                data.setName7("zhangsan"+i1);
                data.setName8("zhangsan"+i1);
                data.setCode(i1+"");
                dataList.add(data);
            }
            map.put("fileName",fileName+i);
            map.put("headers",headers);
            map.put("dataList",dataList);
            mapList.add(map);
        }
        ExcelUtil.exportMultisheetExcel("test",mapList,response);

    }

    @RequestMapping(value = "/readExcel")
    public String readExcel() throws Exception {
        String filePath = "F:\\student.xls";
        List<Map<String, String>> mapList = ExcelUtil.readExcel(filePath, 0);
        log.info("mapList:" + mapList);
        return "success";
    }


    public static void loanDetail(){
        String filePath = "E:\\xmjr明细\\12月小马平台凭证提取明细802笔 - 放款凭证.xlsx";
        List<Map<String,String>> list = Lists.newArrayList();
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
                list.addAll(result.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            exec.shutdown();
        }
        System.out.println(JSON.toJSONString(list));
    }

    public static void main(String args[]){
        String filePath = "E:\\xmjr明细\\12月小马平台凭证提取明细802笔-资金划转证明.xlsx";
        List<Map<String,String>> list = Lists.newArrayList();
        ExecutorService exec = Executors.newFixedThreadPool(8);
        Workbook wb = ExcelUtil.createWorkBook(filePath);
        ArrayList<Future<List<Map<String, String>>>> results = new ArrayList<Future<List<Map<String, String>>>>();
        for (int i = 1; i < 803; i++) {
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
                list.addAll(result.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            exec.shutdown();
        }
        System.out.println(JSON.toJSONString(list));
    }


    @RequestMapping("transferTotal")
    @ResponseBody
    public void method3(){
        transferDetailService.dealTotal();
    }


}
