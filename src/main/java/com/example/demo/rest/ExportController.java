package com.example.demo.rest;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.example.demo.entity.Person;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;

/**
 * @program: demo
 * @description: 导出控制器
 * @author: py
 * @create: 2019-12-31 11:20
 **/
@Controller
public class ExportController {
    @RequestMapping("exportE")
    public void method(HttpServletResponse response) {
        String fileName = "个人信息.xls";
        List<Person> list = Lists.newArrayList();
        Person p1 = new Person(1, "lks1", 23, 1);
        Person p2 = new Person(2, "lks2", 23, 0);
        Person p3 = new Person(3, "lks3", 23, 1);
        Person p4 = new Person(4, "lks4", 23, 0);
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        try (Workbook wb = ExcelExportUtil.exportExcel(new ExportParams("个人信息", "person"), Person.class, list)) {
            response.setCharacterEncoding("utf-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            wb.write(response.getOutputStream());
        } catch (Exception e) {
        }

    }

    @RequestMapping("import")
    @ResponseBody
    public void method() {
        String filePath = "user/个人信息.xlsx";
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(11);
        importParams.setHeadRows(1);
        List<Person> list = ExcelImportUtil.importExcel(new File(filePath), Person.class, importParams);

    }

    public static void main(String args[]){
        System.gc();
    }
}
