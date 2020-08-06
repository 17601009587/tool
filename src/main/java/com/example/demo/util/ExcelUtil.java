package com.example.demo.util;

import com.example.demo.entity.TransferDetailTotal;
import com.example.demo.entity.YydkRepayment;
import com.example.demo.entity.YydkRepaymentDetail;
import com.example.demo.entity.YydkRepaymentTotal;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel导入导出
 *
 * @Author: guandezhi
 * @Date: 2019/3/9 9:47
 */
public class ExcelUtil {

    /**
     * 导出多个sheet的excel
     *
     * @param name
     * @param mapList
     * @param response
     * @param <T>
     */
    public static <T> void exportMultisheetExcel(String name, List<Map> mapList, HttpServletResponse response) {
        BufferedOutputStream bos = null;
        try {
            String fileName = name + ".xlsx";
            bos = getBufferedOutputStream(fileName, response);
            doExport(mapList, bos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> void exportMultisheetTranExcel(String name, List<Map> mapList, HttpServletResponse response) {
        BufferedOutputStream bos = null;
        try {
            String fileName = name + ".xlsx";
            bos = getBufferedOutputStream(fileName, response);
            doTranExport(mapList, bos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从excel中读内容
     *
     * @param filePath
     * @param sheetIndex
     * @return
     */
    public static List<Map<String, String>> readExcel(String filePath, Integer sheetIndex) {
        List<Map<String, String>> dataList = new ArrayList<>();
        Workbook wb = ExcelUtil.createWorkBook(filePath);
        if (wb != null) {
            Sheet sheet = wb.getSheetAt(sheetIndex);
            int maxRownum = sheet.getPhysicalNumberOfRows();
            Row firstRow = sheet.getRow(1);
            int maxColnum = firstRow.getPhysicalNumberOfCells();
            String columns[] = new String[maxColnum];
            Map<String, String> map = null;
            for (int i = 1; i < maxRownum; i++) {
                if (i > 1) {
                    map = new LinkedHashMap<>();
                    firstRow = sheet.getRow(i);
                }
                if (firstRow != null) {
                    String cellData = null;
                    for (int j = 0; j < maxColnum; j++) {
                        cellData = (String) ExcelUtil.getCellFormatValue(firstRow.getCell(j));
                        if (StringUtils.isBlank(cellData)) {
                            break;
                        }
                        if (i == 1) {
                            columns[j] = cellData;
                        } else {
                            map.put(columns[j], cellData);
                        }
                    }
                } else {
                    break;
                }
                if (i > 1) {
                    if (map.size() == 0) {
                        break;
                    }
                    dataList.add(map);
                }
            }
        }
        return dataList;
    }

    public static List<Map<String, String>> readExcelContent(Workbook wb, Integer sheetIndex) {
        List<Map<String, String>> dataList = new ArrayList<>();
        if (wb != null) {
            Sheet sheet = wb.getSheetAt(sheetIndex);
            String sheetName = sheet.getSheetName();
            int maxRownum = sheet.getPhysicalNumberOfRows();
            Row firstRow = sheet.getRow(1);
            int maxColnum = firstRow.getPhysicalNumberOfCells();
            String columns[] = new String[maxColnum];
            Map<String, String> map = null;
            for (int i = 1; i < maxRownum; i++) {
                if (i > 1) {
                    map = new LinkedHashMap<>();
                    firstRow = sheet.getRow(i);
                }
                if (firstRow != null) {
                    String cellData = null;
                    for (int j = 0; j < maxColnum; j++) {
                        cellData = (String) ExcelUtil.getCellFormatValue(firstRow.getCell(j));
                        if (StringUtils.isBlank(cellData)) {
                            break;
                        }
                        if (i == 1) {
                            cellData = getCellName(cellData);
                            columns[j] = cellData;
                        } else {
                            map.put(columns[j], cellData);
                        }
                    }
                } else {
                    break;
                }
                if (i > 1) {
                    if (map.size() == 0) {
                        break;
                    }
                    map.put("name", sheetName);
                    dataList.add(map);
                }
            }
        }
        return dataList;
    }

    public static List<Map<String, String>> readExcelTransferContent(Workbook wb, Integer sheetIndex) {
        List<Map<String, String>> dataList = new ArrayList<>();
        if (wb != null) {
            Sheet sheet = wb.getSheetAt(sheetIndex);
            String sheetName = sheet.getSheetName();
            int maxRownum = sheet.getPhysicalNumberOfRows();
            Row firstRow = sheet.getRow(10);
            int maxColnum = firstRow.getPhysicalNumberOfCells();
            String columns[] = new String[maxColnum];
            Map<String, String> map = null;
            for (int i = 10; i < maxRownum; i++) {
                if (i > 10) {
                    map = new LinkedHashMap<>();
                    firstRow = sheet.getRow(i);
                }
                if (firstRow != null) {
                    String cellData = null;
                    for (int j = 1; j < maxColnum; j++) {
                        cellData = (String) ExcelUtil.getCellFormatValue(firstRow.getCell(j));
                        if (StringUtils.isBlank(cellData)) {
                            break;
                        }
                        if (i == 10) {
                            cellData = getTransferCellName(cellData);
                            columns[j] = cellData;
                        } else {
                            map.put(columns[j], cellData);
                        }
                    }
                } else {
                    break;
                }
                if (i > 10) {
                    if (map.size() == 0) {
                        break;
                    }
                    map.put("name", sheetName);
                    dataList.add(map);
                }
            }
        }
        return dataList;
    }

    private static String getTransferCellName(String cellData) {
        if (cellData.trim().equals("序号")) {
            cellData = "no";
        }
        if (cellData.trim().equals("代偿日期")) {
            cellData = "compensation_date";
        }
        if (cellData.trim().equals("标的编号")) {
            cellData = "subject_number";
        }
        if (cellData.trim().equals("出借人姓名")) {
            cellData = "lender_name";
        }
        if (cellData.trim().equals("身份证号")) {
            cellData = "lender_idNo";
        }
        if (cellData.trim().equals("手机号")) {
            cellData = "lender_mobieleNo";
        }
        if (cellData.trim().equals("出借人账户（二类电子账户）")) {
            cellData = "lender_account";
        }
        if (cellData.trim().equals("出借金额")) {
            cellData = "lender_amount";
        }
        if (cellData.trim().equals("小马汇入金额") || cellData.trim().equals("代偿汇入金额")) {
            cellData = "compensation_amount";
        }
        if (cellData.trim().equals("借款人汇入金额")) {
            cellData = "repay_amount";
        }
        return cellData;
    }

    private static String getCellName(String cellData) {
        if (cellData.trim().equals("放款日期")) {
            cellData = "loan_time";
        }
        if (cellData.trim().equals("支付币种")) {
            cellData = "currency";
        }
        if (cellData.trim().equals("金额小写")) {
            cellData = "amount";
        }
        if (cellData.trim().equals("放款户名")) {
            cellData = "loan_name";
        }
        if (cellData.trim().equals("放款账号")) {
            cellData = "loan_account";
        }
        if (cellData.trim().equals("放款银行")) {
            cellData = "loan_bank";
        }
        if (cellData.trim().equals("收款户名")) {
            cellData = "recevi_name";
        }
        if (cellData.trim().equals("收款账号")) {
            cellData = "recevi_account";
        }
        if (cellData.trim().equals("收款开户行")) {
            cellData = "recevi_bank";
        }
        if (cellData.trim().equals("用途")) {
            cellData = "purpose";
        }
        return cellData;
    }

    private static BufferedOutputStream getBufferedOutputStream(String fileName, HttpServletResponse response) throws Exception {
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        return new BufferedOutputStream(response.getOutputStream());
    }

    private static <T> void doTranExport(List<Map> mapList, OutputStream outputStream) {
        int maxBuff = 300;
        // 创建excel工作文本，100表示默认允许保存在内存中的行数
        SXSSFWorkbook wb = new SXSSFWorkbook(maxBuff);
        String[] exportFields = {"compensationDate", "subjectNumber", "lenderName", "lenderIdNo", "lenderMobieleNo", "lenderAccount", "lenderAmount", "compensationAmount", "repayAmount"};
        try {
            for (int i = 0; i < mapList.size(); i++) {
                Map map = mapList.get(i);
                String[] headers = (String[]) map.get("headers");
                Collection<T> dataList = (Collection<T>) map.get("dataList");
                String fileName = (String) map.get("fileName");
                String no = (String) map.get("no");
                TransferDetailTotal transferDetailTotal = (TransferDetailTotal) map.get("row");
                createTranSheet(wb, exportFields, headers, dataList, fileName, maxBuff, transferDetailTotal, no);
            }
            if (outputStream != null) {
                wb.write(outputStream);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static <T> void doExport(List<Map> mapList, OutputStream outputStream) {
        int maxBuff = 300;
        // 创建excel工作文本，100表示默认允许保存在内存中的行数
        SXSSFWorkbook wb = new SXSSFWorkbook(maxBuff);
        String[] exportFields = {"loanTime", "currency", "amount", "loanName", "loanAccount", "loanBank", "receviName", "receviAccount", "receviBank", "purpose"};
        try {
            for (int i = 0; i < mapList.size(); i++) {
                Map map = mapList.get(i);
                String[] headers = (String[]) map.get("headers");
                Collection<T> dataList = (Collection<T>) map.get("dataList");
                String fileName = (String) map.get("fileName");
                String no = (String) map.get("no");
                createSheet(wb, exportFields, headers, dataList, fileName, maxBuff, no);
            }
            if (outputStream != null) {
                wb.write(outputStream);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static <T> void createTranSheet(SXSSFWorkbook wb, String[] exportFields, String[] headers, Collection<T> dataList, String fileName, int maxBuff, TransferDetailTotal transferDetailTotal, String no) throws NoSuchFieldException, IllegalAccessException, IOException {
        SXSSFSheet sh = wb.createSheet(fileName);
        //sh.trackAllColumnsForAutoSizing();
        sh.setRandomAccessWindowSize(-1);
        PrintSetup ps = sh.getPrintSetup();
        ps.setLandscape(true); // 打印方向，true：横向，false：纵向
        ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //纸张
        sh.setMargin(HSSFSheet.BottomMargin, (double) 0.5);// 页边距（下）
        sh.setMargin(HSSFSheet.LeftMargin, (double) 0.1);// 页边距（左）
        sh.setMargin(HSSFSheet.RightMargin, (double) 0.1);// 页边距（右）
        sh.setMargin(HSSFSheet.TopMargin, (double) 0.5);// 页边距（上）
        sh.setHorizontallyCenter(true);//设置打印页面为水平居中
        sh.setVerticallyCenter(true);
        CellStyle style = wb.createCellStyle();
        CellStyle style1 = wb.createCellStyle();
        CellStyle style2 = wb.createCellStyle();
        CellStyle style3 = wb.createCellStyle();
        CellStyle style4 = wb.createCellStyle();
        //创建表头
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);//设置字体大小
        Font font1 = wb.createFont();
        font1.setFontName("宋体");
        font1.setBold(true);
        font1.setFontHeightInPoints((short) 18);//设置字体大小
        style.setFont(font);//选择需要用到的字体格式
        style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style1.setFont(font1);//选择需要用到的字体格式
        style1.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style2.setFont(font);//选择需要用到的字体格式
        style2.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平向下居中
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style2.setWrapText(true);
        style3.setFont(font);//选择需要用到的字体格式
        style3.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
        style3.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 水平向下居中
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style3.setWrapText(true);
        style4.setFont(font);//选择需要用到的字体格式
        style4.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
        style4.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 水平向下居中
        style4.setWrapText(true);
        CellRangeAddress cra = new CellRangeAddress(1, 1, 0, 9);
        sh.addMergedRegion(cra);
        CellRangeAddress cra2 = new CellRangeAddress(2, 3, 0, 9);
        sh.addMergedRegion(cra2);
        CellRangeAddress cra3 = new CellRangeAddress(4, 5, 0, 9);
        sh.addMergedRegion(cra3);
        Row headRow = sh.createRow(0); //表头
        Cell cell8 = headRow.createCell(0);
        cell8.setCellStyle(style4);
        cell8.setCellValue("编号：" + no);
        Row headerRow = sh.createRow(1); //表头
        Cell cell1 = headerRow.createCell(0);
        cell1.setCellStyle(style1);
        cell1.setCellValue("资金划转证明");
        Row SecondRow = sh.createRow(2); //表头
        Cell cell2 = SecondRow.createCell(0);
        cell2.setCellStyle(style3);
        cell2.setCellValue("    根据《包商银行网络借贷交易资金存管协议》、《包商银行网络借贷交易资金存管三方协议》，北京小马金融信息服务有限公司、借款人、出借人选择我行作为网络借贷交易资金存管合作银行，办理用户网络借贷交易资金的接收、存放、划转业务，并提供相关配套服务。");
        Row thirdRow = sh.createRow(4); //表头
        Cell cell3 = thirdRow.createCell(0);
        cell3.setCellStyle(style3);
        cell3.setCellValue("    北京小马金融信息服务有限公司通过我行发送指令的方式从账户信息：户名：" + transferDetailTotal.getAccountName() + ",账号:" + transferDetailTotal.getAccount() + "向出借人如下账户划转人民币总计：" + transferDetailTotal.getAmount() + "元（大写:" + transferDetailTotal.getAccountStr() + "）。");
        int headerSize = headers.length;
        Row fourRow = sh.createRow(6);
        for (int cellnum = 0; cellnum < headerSize; cellnum++) {
            Cell cell = fourRow.createCell(cellnum);
            cell.setCellStyle(style);
            sh.setColumnWidth(cellnum, 3500);
            fourRow.setHeight((short) 400);
            cell.setCellValue(headers[cellnum]);
            /*RichTextString text = new XSSFRichTextString(headers[cellnum]);
            cell.setCellValue(text);
            sh.autoSizeColumn(cellnum);
            sh.setColumnWidth(cellnum, sh.getColumnWidth(cellnum) * 13 / 10);*/
        }
        int rownum = 6;
        Iterator<T> iterator = dataList.iterator();
        int k = 1;
        while (iterator.hasNext()) {
            T data = iterator.next();
            Row row = sh.createRow(rownum + 1);
            row.setHeight((short) 560);
            Field[] fields = getExportFields(data.getClass(), exportFields);
            for (int cellnum = 0; cellnum < headerSize; cellnum++) {
                Cell cell = row.createCell(cellnum);
                cell.setCellStyle(style2);
                if (cellnum == 0) {
                    cell.setCellValue(k++);
                } else {
                    Field field = fields[cellnum - 1];
                    setData(field, data, field.getName(), cell);
                }

            }
            rownum = sh.getLastRowNum();
            // 大数据量时将之前的数据保存到硬盘
            if (rownum % maxBuff == 0) {
                ((SXSSFSheet) sh).flushRows(maxBuff); // 超过100行后将之前的数据刷新到硬盘

            }
        }
        CellRangeAddress cra4 = new CellRangeAddress(rownum + 1, rownum + 6, 0, 9);
        sh.addMergedRegion(cra4);
        Row lastRow1 = sh.createRow(rownum + 1); //表头
        Cell cell5 = lastRow1.createCell(0);
        cell5.setCellStyle(style3);
        cell5.setCellValue("      特此证明！\n" +
                "\n" +
                "\n" +
                "                                                                                                 包商银行股份有限公司\n" +
                "                                                                                                    年    月    日  ");
        System.out.println("导出sheet:" + fileName);
        RegionUtil.setBorderBottom(1, cra, sh); // 下边框
        RegionUtil.setBorderLeft(1, cra, sh); // 左边框
        RegionUtil.setBorderRight(1, cra, sh); // 有边框
        RegionUtil.setBorderTop(1, cra, sh); // 上边框
        RegionUtil.setBorderBottom(1, cra2, sh); // 下边框
        RegionUtil.setBorderLeft(1, cra2, sh); // 左边框
        RegionUtil.setBorderRight(1, cra2, sh); // 有边框
        RegionUtil.setBorderTop(1, cra2, sh); // 上边框
        RegionUtil.setBorderBottom(1, cra3, sh); // 下边框
        RegionUtil.setBorderLeft(1, cra3, sh); // 左边框
        RegionUtil.setBorderRight(1, cra3, sh); // 有边框
        RegionUtil.setBorderTop(1, cra3, sh); // 上边框
        RegionUtil.setBorderBottom(1, cra4, sh); // 下边框
        RegionUtil.setBorderLeft(1, cra4, sh); // 左边框
        RegionUtil.setBorderRight(1, cra4, sh); // 有边框
        RegionUtil.setBorderTop(1, cra4, sh); // 上边框

    }

    private static <T> void createSheet(SXSSFWorkbook wb, String[] exportFields, String[] headers, Collection<T> dataList, String fileName, int maxBuff, String no) throws NoSuchFieldException, IllegalAccessException, IOException {
        SXSSFSheet sh = wb.createSheet(fileName);
        //sh.trackAllColumnsForAutoSizing();
        PrintSetup ps = sh.getPrintSetup();
        ps.setLandscape(true); // 打印方向，true：横向，false：纵向
        ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //纸张
        sh.setMargin(HSSFSheet.BottomMargin, (double) 0.5);// 页边距（下）
        sh.setMargin(HSSFSheet.LeftMargin, (double) 0.1);// 页边距（左）
        sh.setMargin(HSSFSheet.RightMargin, (double) 0.1);// 页边距（右）
        sh.setMargin(HSSFSheet.TopMargin, (double) 0.5);// 页边距（上）
        sh.setHorizontallyCenter(true);//设置打印页面为水平居中
        sh.setVerticallyCenter(true);
        CellStyle style = wb.createCellStyle();
        CellStyle style1 = wb.createCellStyle();
        CellStyle style2 = wb.createCellStyle();
        CellStyle style3 = wb.createCellStyle();
        //创建表头
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 10);//设置字体大小
        Font font1 = wb.createFont();
        font1.setFontName("宋体");
        font1.setBold(true);
        font1.setFontHeightInPoints((short) 18);//设置字体大小
        style.setFont(font);//选择需要用到的字体格式
        style.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style1.setFont(font1);//选择需要用到的字体格式
        style1.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style2.setFont(font);//选择需要用到的字体格式
        style2.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平向下居中
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style2.setWrapText(true);
        style3.setFont(font);//选择需要用到的字体格式
        style3.setFillForegroundColor(HSSFColor.WHITE.index);// 设置背景色
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); //垂直居中
        style3.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 水平向下居中
        CellRangeAddress cra = new CellRangeAddress(1, 1, 0, 9);
        sh.addMergedRegion(cra);
        Row headerRow = sh.createRow(0); //表头
        Cell cell2 = headerRow.createCell(0);
        cell2.setCellStyle(style3);
        cell2.setCellValue("编号：" + no);
        Row headRow = sh.createRow(1); //表头
        Cell cell1 = headRow.createCell(0);
        cell1.setCellStyle(style1);
        cell1.setCellValue("放款凭证");
        Row SecondRow = sh.createRow(2); //表头
        int headerSize = headers.length;
        for (int cellnum = 0; cellnum < headerSize; cellnum++) {
            Cell cell = SecondRow.createCell(cellnum);
            cell.setCellStyle(style);
            sh.setColumnWidth(cellnum, 3500);
            SecondRow.setHeight((short) 400);
            cell.setCellValue(headers[cellnum]);
            /*RichTextString text = new XSSFRichTextString(headers[cellnum]);
            cell.setCellValue(text);
            sh.autoSizeColumn(cellnum);
            sh.setColumnWidth(cellnum, sh.getColumnWidth(cellnum) * 13 / 10);*/
        }
        int rownum = 2;
        Iterator<T> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            T data = iterator.next();
            Row row = sh.createRow(rownum + 1);
            row.setHeight((short) 560);
            Field[] fields = getExportFields(data.getClass(), exportFields);
            for (int cellnum = 0; cellnum < headerSize; cellnum++) {
                Cell cell = row.createCell(cellnum);
                cell.setCellStyle(style2);
                Field field = fields[cellnum];
                setData(field, data, field.getName(), cell);
            }
            rownum = sh.getLastRowNum();
            // 大数据量时将之前的数据保存到硬盘
            if (rownum % maxBuff == 0) {
                ((SXSSFSheet) sh).flushRows(maxBuff); // 超过100行后将之前的数据刷新到硬盘

            }
        }
        RegionUtil.setBorderBottom(1, cra, sh); // 下边框
        RegionUtil.setBorderLeft(1, cra, sh); // 左边框
        RegionUtil.setBorderRight(1, cra, sh); // 有边框
        RegionUtil.setBorderTop(1, cra, sh); // 上边框
    }

    private static <T> void doExport(String[] headers, String[] exportFields, Collection<T> dataList,
                                     String fileName, OutputStream outputStream, String no) {
        int maxBuff = 100;
        // 创建excel工作文本，100表示默认允许保存在内存中的行数
        SXSSFWorkbook wb = new SXSSFWorkbook(maxBuff);
        try {
            createSheet(wb, exportFields, headers, dataList, fileName, maxBuff, no);
            if (outputStream != null) {
                wb.write(outputStream);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取单条数据的属性
     *
     * @param object
     * @param property
     * @param <T>
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static <T> Field getDataField(T object, String property) throws NoSuchFieldException, IllegalAccessException {
        Field dataField;
        if (property.contains(".")) {
            String p = property.substring(0, property.indexOf("."));
            dataField = object.getClass().getDeclaredField(p);
            return dataField;
        } else {
            dataField = object.getClass().getDeclaredField(property);
        }
        return dataField;
    }

    private static Field[] getExportFields(Class<?> targetClass, String[] exportFieldNames) {
        Field[] fields = null;
        if (exportFieldNames == null || exportFieldNames.length < 1) {
            fields = targetClass.getDeclaredFields();
        } else {
            fields = new Field[exportFieldNames.length];
            for (int i = 0; i < exportFieldNames.length; i++) {
                try {
                    fields[i] = targetClass.getDeclaredField(exportFieldNames[i]);
                } catch (Exception e) {
                    try {
                        fields[i] = targetClass.getSuperclass().getDeclaredField(exportFieldNames[i]);
                    } catch (Exception e1) {
                        throw new IllegalArgumentException("无法获取导出字段", e);
                    }

                }
            }
        }
        return fields;
    }

    /**
     * 根据属性设置对应的属性值
     *
     * @param dataField 属性
     * @param object    数据对象
     * @param property  表头的属性映射
     * @param cell      单元格
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    private static <T> void setData(Field dataField, T object, String property, Cell cell)
            throws IllegalAccessException, NoSuchFieldException {
        dataField.setAccessible(true); //允许访问private属性
        Object val = dataField.get(object); //获取属性值
        Sheet sh = cell.getSheet(); //获取excel工作区
        CellStyle style = cell.getCellStyle(); //获取单元格样式
        int cellnum = cell.getColumnIndex();
        if (val != null) {
            if (dataField.getType().toString().endsWith("String")) {
                cell.setCellValue((String) val);
            } else if (dataField.getType().toString().endsWith("Integer") || dataField.getType().toString().endsWith("int")) {
                cell.setCellValue((Integer) val);
            } else if (dataField.getType().toString().endsWith("Long") || dataField.getType().toString().endsWith("long")) {
                cell.setCellValue(val.toString());
            } else if (dataField.getType().toString().endsWith("Double") || dataField.getType().toString().endsWith("double")) {
                cell.setCellValue((Double) val);
            } else if (dataField.getType().toString().endsWith("Date")) {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cell.setCellValue(format.format((Date) val));
            } else if (dataField.getType().toString().endsWith("List")) {
                List list1 = (List) val;
                int size = list1.size();
                for (int i = 0; i < size; i++) {
                    //加1是因为要去掉点号
                    int start = property.indexOf(dataField.getName()) + dataField.getName().length() + 1;
                    String tempProperty = property.substring(start, property.length());
                    Field field = getDataField(list1.get(i), tempProperty);
                    Cell tempCell = cell;
                    if (i > 0) {
                        int rowNum = cell.getRowIndex() + i;
                        Row row = sh.getRow(rowNum);
                        if (row == null) {//另起一行
                            row = sh.createRow(rowNum);
                            //合并之前的空白单元格（在这里需要在header中按照顺序把list类型的字段放到最后，方便显示和合并单元格）
                            for (int j = 0; j < cell.getColumnIndex(); j++) {
                                sh.addMergedRegion(new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex() + size - 1, j, j));
                                Cell c = row.createCell(j);
                                c.setCellStyle(style);
                            }
                        }
                        tempCell = row.createCell(cellnum);
                        tempCell.setCellStyle(style);
                    }
                    //递归传参到单元格并获取偏移量（这里获取到的偏移量都是第二层后list的偏移量）
                    setData(field, list1.get(i), tempProperty, tempCell);
                }
            } else {
                if (property.contains(".")) {
                    String p = property.substring(property.indexOf(".") + 1, property.length());
                    Field field = getDataField(val, p);
                    setData(field, val, p, cell);
                } else {
                    cell.setCellValue(val.toString());
                }
            }
        }
    }

    public static Workbook createWorkBook(String filePath) {
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                return wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return wb = new XSSFWorkbook(is);
            } else {
                return wb;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    /**
     * 将字段转为相应的格式
     *
     * @param cell
     * @return
     */
    public static Object getCellFormatValue(Cell cell) {
        return getCellFormatValue(cell,null);
    }

    static Object getCellFormatValue(Cell cell,HSSFFormulaEvaluator evaluator) {
        Object cellValue = null;
        if (cell != null) {
            //判断cell类型
            /*switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC: {

                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case Cell.CELL_TYPE_FORMULA: {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = cell.getDateCellValue();////转换为日期格式YYYY-mm-dd
                    } else {
                        cellValue = String.valueOf(cell.getNumericCellValue()); //数字
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }*/
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                Date d = cell.getDateCellValue();
                DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                cellValue = formater.format(d);
            } else {

                if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){
                    CellValue tempCellValue = evaluator.evaluate(cell);
                    cellValue = String.valueOf(tempCellValue.getNumberValue());
                }else {
                    cell.setCellType(CellType.STRING);
                    cellValue = cell.getStringCellValue();
                }
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

    public static YydkRepaymentTotal readPaymentContent(Workbook wb) {
        YydkRepaymentTotal yydkRepaymentTotal = new YydkRepaymentTotal();
        HSSFFormulaEvaluator evaluator=new HSSFFormulaEvaluator((HSSFWorkbook) wb);
        List<YydkRepaymentDetail> list = Lists.newArrayList();
        if (wb != null) {
            Sheet sheet = wb.getSheetAt(0);
            int maxRownum = sheet.getPhysicalNumberOfRows();
            Row firstRow = null;
            YydkRepayment yydkRepayment = new YydkRepayment();
            YydkRepaymentDetail yydkRepaymentDetail = null;
            Row lastrow = sheet.getRow(sheet.getLastRowNum());
            yydkRepayment.setTotalAmount(new BigDecimal(ExcelUtil.getCellFormatValue(lastrow.getCell(2),evaluator) + ""));
            yydkRepayment.setTotalPrincipal(new BigDecimal(ExcelUtil.getCellFormatValue(lastrow.getCell(3),evaluator) + ""));
            yydkRepayment.setTotalInterestFee(new BigDecimal(ExcelUtil.getCellFormatValue(lastrow.getCell(4),evaluator) + ""));
            yydkRepayment.setTotalDeduction(new BigDecimal(ExcelUtil.getCellFormatValue(lastrow.getCell(5),evaluator) + ""));
            for (int i = 1; i < maxRownum; i++) {
                firstRow = sheet.getRow(i);
                if (i == 1) {
                    yydkRepayment.setName((String) ExcelUtil.getCellFormatValue(firstRow.getCell(1)));
                    yydkRepayment.setContractNo((String) ExcelUtil.getCellFormatValue(firstRow.getCell(3)));
                    yydkRepayment.setAccount((String) ExcelUtil.getCellFormatValue(firstRow.getCell(5)));
                    continue;
                }
                if (i == 2) {
                    yydkRepayment.setAmount(new BigDecimal((String) ExcelUtil.getCellFormatValue(firstRow.getCell(1))));
                    yydkRepayment.setPayment((String) ExcelUtil.getCellFormatValue(firstRow.getCell(3)));
                    continue;
                }
                if (i == 3) {
                    yydkRepayment.setLoanTime((String) ExcelUtil.getCellFormatValue(firstRow.getCell(1)));
                    yydkRepayment.setOffTime((String) ExcelUtil.getCellFormatValue(firstRow.getCell(3)));
                    continue;
                }
                if (i == 4) {
                    yydkRepayment.setPeriod(Integer.parseInt(ExcelUtil.getCellFormatValue(firstRow.getCell(1)) + ""));
                    yydkRepayment.setCunt(Integer.parseInt(ExcelUtil.getCellFormatValue(firstRow.getCell(3)) + ""));
                    yydkRepayment.setLncfno((String) ExcelUtil.getCellFormatValue(firstRow.getCell(5)));
                    continue;
                }
                if (i >= 7 &&i < maxRownum - 1) {

                    yydkRepaymentDetail = new YydkRepaymentDetail();
                    yydkRepaymentDetail.setLncfno(yydkRepayment.getLncfno());
                    yydkRepaymentDetail.setPeriod(Integer.parseInt(ExcelUtil.getCellFormatValue(firstRow.getCell(0)) + ""));
                    if(!"0".equals((String) ExcelUtil.getCellFormatValue(firstRow.getCell(1)))){
                        yydkRepaymentDetail.setRepaymentTime((String) ExcelUtil.getCellFormatValue(firstRow.getCell(1)));
                    }
                    yydkRepaymentDetail.setAmount(new BigDecimal((String) ExcelUtil.getCellFormatValue(firstRow.getCell(2),evaluator)));
                    yydkRepaymentDetail.setPrincipal(new BigDecimal((String) ExcelUtil.getCellFormatValue(firstRow.getCell(3))));
                    yydkRepaymentDetail.setInterestFee(new BigDecimal((String) ExcelUtil.getCellFormatValue(firstRow.getCell(4))));
                    yydkRepaymentDetail.setDeduction(new BigDecimal((String) ExcelUtil.getCellFormatValue(firstRow.getCell(5))));
                    list.add(yydkRepaymentDetail);
                }
            }
            yydkRepaymentTotal.setYydkRepayment(yydkRepayment);
            yydkRepaymentTotal.setYydkRepaymentDetails(list);
        }
        return yydkRepaymentTotal;
    }
}
