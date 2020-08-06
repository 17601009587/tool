package com.example.demo.rest.excel;

import com.example.demo.service.YydkFileService;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @description:
 * @author: py
 * @create: 2020-01-09 17:26
 **/
@RestController
@RequestMapping("/yydk")
public class YydkFileController {
    @Autowired
    private YydkFileService yydkFileService;

    @RequestMapping("m")
    @ResponseBody
    public void method() throws IOException {
        yydkFileService.handle();
    }
    @RequestMapping("payment")
    @ResponseBody
    public void method5() throws IOException {
        yydkFileService.payment();
    }

    @RequestMapping("test")
    @ResponseBody
    public String method8(){
        return yydkFileService.test();
    }


    public static void readAndWriterTest4(String path) throws IOException {
        File file = new File(path);
        String str = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument xdoc = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            String doc1 = extractor.getText();
            System.out.println(doc1);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        readAndWriterTest4("D:\\代偿凭证-阿拉达日图-802000015039168.docx");
        readAndWriterTest4("D:\\放款凭证-阿拉达日图-802000015039168.docx");
    }

}
