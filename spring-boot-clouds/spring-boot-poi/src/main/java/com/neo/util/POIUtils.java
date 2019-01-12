package com.neo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neo.service.LocaleMessageSourceService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
public class POIUtils {
    // 大纲字体
    private static final short HEADER_FONT_SIZE = 16;
    // 行首字体
    private static final short FONT_HEIGHT_IN_POINTS = 14;

    private static final int MEM_ROW  = 100;

    public static Workbook createWorkbook(String file) {
        Workbook wb = createSXSSFWorkbook(MEM_ROW);
        return wb;
    }

    public static Workbook createWorkbookByIS(String file, InputStream inputStream) {
        String ext = FilenameUtils.getExtension(file);
        Workbook wb = null;
        try {
            OPCPackage p = OPCPackage.open(inputStream);
            wb = new SXSSFWorkbook(new XSSFWorkbook(p), 100);
        } catch (Exception ex) {
            try {
                wb = new HSSFWorkbook(inputStream, false);
            } catch (IOException e) {
                wb = new XSSFWorkbook();
            }
        }
        return wb;
    }

    /**
     *
     * @param wb
     * @param file
     * @return
     */

    public static Workbook writeFile(Workbook wb, String file) {
        if (wb == null || Utils.isEmpty(file)) {
            return null;
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return wb;
    }

    public static Workbook createHSSFWorkbook() {
        //生成Workbook
        HSSFWorkbook wb = new HSSFWorkbook();
        //添加Worksheet（不添加sheet时生成的xls文件打开时会报错）
        @SuppressWarnings("unused")
        Sheet sheet = wb.createSheet();
        return wb;
    }

    public static Workbook createSXSSFWorkbook(int memRow) {
        Workbook wb = new SXSSFWorkbook(memRow);
        Sheet sheet = wb.createSheet();
        return wb;
    }

    public static Workbook createXSSFWorkbook() {
        XSSFWorkbook wb = new XSSFWorkbook();
        @SuppressWarnings("unused")
        Sheet sheet = wb.createSheet();
        return wb;
    }



    private  static  Workbook  openWorkbook(FileInputStream in){
        Workbook wb = null;
        try {
            if(in == null){
                return  wb;
            }
            wb = WorkbookFactory.create(in);
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wb;
    }
    public static Workbook openWorkbook(File file) throws Exception{
        FileInputStream  in = new FileInputStream(file);
        return  openWorkbook(in);
    }
    public static Workbook openWorkbook(String file) throws Exception{
        FileInputStream  in = new FileInputStream(file);
        return  openWorkbook(in);
    }

    public static Workbook openEncryptedWorkbook(String file, String password) {
        FileInputStream input = null;
        BufferedInputStream binput = null;
        POIFSFileSystem poifs = null;
        Workbook wb = null;
        try {
            input = new FileInputStream(file);
            binput = new BufferedInputStream(input);
            poifs = new POIFSFileSystem(binput);
            Biff8EncryptionKey.setCurrentUserPassword(password);
            String ext = FilenameUtils.getExtension(file);
            switch (ext) {
                case "xls":
                    wb = new HSSFWorkbook(poifs);
                    break;
                case "xlsx":
                    wb = new XSSFWorkbook(input);
                    break;
                default:
                    wb = new HSSFWorkbook(poifs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    /**
     * 追加一个sheet,如果wb为空且isNew为true,创建一个wb
     *
     * @param wb
     * @param isNew
     * @param type  创建wb类型,isNew为true时有效 1:xls,2:xlsx
     * @return
     */
    public static Workbook appendSheet(Workbook wb, boolean isNew, int type) {
        if (wb != null) {
            Sheet sheet = wb.createSheet();
        } else if (isNew) {
            if (type == 1) {
                wb = new HSSFWorkbook();
                wb.createSheet();
            } else {
                wb = new XSSFWorkbook();
                wb.createSheet();
            }
        }
        return wb;
    }

    public static Workbook setSheetName(Workbook wb, int index, String sheetName) {
        if (wb != null && wb.getSheetAt(index) != null) {
            wb.setSheetName(index, sheetName);
        }
        return wb;
    }
    public static void insert(Sheet sheet, int row, int start, List<?> columns) {
        for (int i = start; i < (row + start); i++) {
            Row rows = sheet.createRow(i);
            if (columns != null && columns.size() > 0) {
                for (int j = 0; j < columns.size(); j++) {
                    Cell ceil = rows.createCell(j);
                    ceil.setCellValue(String.valueOf(columns.get(j)));
                }
            }
        }
    }

    public static void insertRow(Row row, List<?> columns) {
        if (columns != null && columns.size() > 0) {
            for (int j = 0; j < columns.size(); j++) {
                Cell ceil = row.createCell(j);
                ceil.setCellValue(String.valueOf(columns.get(j)));
            }
        }
    }

    /**
     * 设置excel头部
     * @param wb
     * @param sheetName
     * @param columns   比如:["国家","活动类型","年份"]
     * @return
     */
    public static Workbook setHeader(Workbook wb, String sheetName, List<?> columns) {
        if (wb == null){
            return null;
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheetName == null) {
            sheetName = sheet.getSheetName();
        }
        insert(sheet, 1, 0, columns);
        return setHeaderStyle(wb, sheetName);

    }

    /**
     * 插入数据
     *
     * @param wb        Workbook
     * @param sheetName sheetName,默认为第一个sheet
     * @param start     开始行数
     * @param data      数据,List嵌套List ,比如:[["中国","奥运会",2008],["伦敦","奥运会",2012]]
     * @return
     */
    public static Workbook setData(Workbook wb, String sheetName, int start, List<?> data) {
        if (wb == null){
            return null;
        }
        if (sheetName == null) {
            sheetName = wb.getSheetAt(0).getSheetName();
        }
        if (!Utils.isEmpty(data)) {
            if (data instanceof List) {
                int s = start;
                Sheet sheet = wb.getSheet(sheetName);
                for (Object rowData : data) {
                    Row row = sheet.createRow(s);
                    insertRow(row, (List<?>) rowData);
                    s++;
                }
            }
        }
        return wb;
    }

    /**
     * 移除某一行
     *
     * @param wb
     * @param sheetName sheet name
     * @param row       行号
     * @return
     */
    public static Workbook delRow(Workbook wb, String sheetName, int row) {
        if (wb == null) {
            return null;
        }
        if (sheetName == null) {
            sheetName = wb.getSheetAt(0).getSheetName();
        }
        Row r = wb.getSheet(sheetName).getRow(row);
        wb.getSheet(sheetName).removeRow(r);
        return wb;
    }

    /**
     * 移动行
     *
     * @param wb
     * @param sheetName
     * @param start     开始行
     * @param end       结束行
     * @param step      移动到那一行后(前) ,负数表示向前移动
     *                  moveRow(wb,null,2,3,5); 把第2和3行移到第5行之后
     *                  moveRow(wb,null,2,3,-1); 把第3行和第4行往上移动1行
     * @return
     */
    public static Workbook moveRow(Workbook wb, String sheetName, int start, int end, int step) {
        if (wb == null){
            return null;
        }
        if (sheetName == null) {
            sheetName = wb.getSheetAt(0).getSheetName();
        }
        wb.getSheet(sheetName).shiftRows(start, end, step);
        return wb;
    }

    public static Workbook setHeaderStyle(Workbook wb, String sheetName) {
        Font font = wb.createFont();
        CellStyle style = wb.createCellStyle();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints(FONT_HEIGHT_IN_POINTS);
        font.setFontName("黑体");
        style.setFont(font);
        if (Utils.isEmpty(sheetName)) {
            sheetName = wb.getSheetAt(0).getSheetName();
        }
        int row = wb.getSheet(sheetName).getFirstRowNum();
        int cell = wb.getSheet(sheetName).getRow(row).getLastCellNum();
        for (int i = 0; i < cell; i++) {
            wb.getSheet(sheetName).getRow(row).getCell(i).setCellStyle(style);
        }
        return wb;
    }

    public static Workbook setHeaderOutline(Workbook wb, String sheetName, String title) {
        if (wb == null) {
            return null;
        }
        if (Utils.isEmpty(sheetName)) {
            sheetName = wb.getSheetAt(0).getSheetName();
        }
        Header header = wb.getSheet(sheetName).getHeader();
        StringBuilder  stringBuilder = new StringBuilder( HSSFHeader.startUnderline()).append(HSSFHeader.font("宋体", "Italic")).append("喜迎G20!").append(HSSFHeader.endUnderline());
        header.setLeft(stringBuilder.toString());
                        stringBuilder = new StringBuilder(HSSFHeader.fontSize(HEADER_FONT_SIZE)).append(HSSFHeader.startDoubleUnderline())
                        .append( HSSFHeader.startBold()).append(title).append(HSSFHeader.endBold()).append(HSSFHeader.endDoubleUnderline());
        header.setCenter(stringBuilder.toString());
                        stringBuilder = new StringBuilder("时间:").append(HSSFHeader.date()).append(" ").append( HSSFHeader.time());
        header.setRight(stringBuilder.toString());
        return wb;
    }

    public static Workbook setFooter(Workbook wb, String sheetName, String copyright) {
        if (wb == null){
            return null;
        }
        if (Utils.isEmpty(sheetName)) {
            sheetName = wb.getSheetAt(0).getSheetName();
        }
        Footer footer = wb.getSheet(sheetName).getFooter();
        if (Utils.isEmpty(copyright)) {
            copyright = "joyven";
        }
        footer.setLeft("Copyright @ " + copyright);
        footer.setCenter("Page:" + HSSFFooter.page() + " / " + HSSFFooter.numPages());
        footer.setRight("File:" + HSSFFooter.file());
        return wb;
    }

    public static Workbook create(String sheetNm, String file, List<?> header, List<?> data, String  title, String copyright) {
        Workbook wb = createWorkbook(file);
        if (Utils.isEmpty(sheetNm)) {
            sheetNm = wb.getSheetAt(0).getSheetName();
        }
        setHeaderOutline(wb, sheetNm, title);
        setHeader(wb, sheetNm, header);
        setData(wb, sheetNm, 1, data);
        setFooter(wb, sheetNm, copyright);
        if (wb != null) {
            return wb;
        }
        return null;
    }



}
