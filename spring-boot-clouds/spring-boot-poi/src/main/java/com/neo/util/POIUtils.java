package com.neo.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.*;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class POIUtils {
    // 大纲字体
    private static final short HEADER_FONT_SIZE = 16;
    // 行首字体
    private static final short FONT_HEIGHT_IN_POINTS = 14;

    private static final int MEM_ROW  = 100;

    public static Workbook createWorkbook() {
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
        } catch (IOException e) {
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
            Iterables.forEach(columns,(index,item)-> row.createCell(index).setCellValue(String.valueOf(item)));
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



    public static Workbook setHeaderStyle(Workbook wb, String sheetName) {
        Font font = wb.createFont();
        CellStyle style = wb.createCellStyle();
      /*  font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);*/
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

    public static Workbook create(String sheetNm, List<?> header, List<?> data, String  title, String copyright) {
        Workbook wb = createWorkbook();
        if (Utils.isEmpty(sheetNm)) {
            sheetNm = wb.getSheetAt(0).getSheetName();
        }
      //  setHeaderOutline(wb, sheetNm, title);
        setHeader(wb, sheetNm, header);
      //  setData(wb, sheetNm, 1, data);
        setFooter(wb, sheetNm, copyright);
        if (wb != null) {
            return wb;
        }
        return null;
    }









    public static  void  setFont(String fontName,int fontHeight,boolean boldWeightBold ,Workbook workbook , Cell cell){
         CellStyle  cellStyle = workbook.createCellStyle();
          Font font = workbook.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints((short)fontHeight);
      /*  if(boldWeightBold) {
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);*/
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
    }
    public   static   void   setTitle(String title, Workbook workbook ,Sheet sheet,int lastCol ){
        if(!Utils.isEmpty(title)) {
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue(title);
            setFont("华文行楷", 16, true, workbook, cell);
            CellRangeAddress rang = new CellRangeAddress(0, 0, 0, lastCol-1);
            sheet.addMergedRegion(rang);
        }
    }

    public static  void setQueryRows(String title ,List<?> queryRows,Workbook workbook ,Sheet sheet, int lastCol){
        if(null != queryRows &&  queryRows.size() > 0 ) {
            int rowNum  = 0;
            if(!Utils.isEmpty(title)) {
                rowNum = rowNum +1;
            }
            String cnt = "\r\n";
            StringBuilder content = new StringBuilder();
            queryRows.forEach(item -> {
                content.append(item).append(cnt);
            });
            Row row = sheet.createRow(rowNum);
            Cell cell = row.createCell(0);
             CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setWrapText(true);
           // setCellStyle(cellStyle);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(new HSSFRichTextString(content.substring(0,content.length()-2)));
            CellRangeAddress rang = new CellRangeAddress(rowNum, (queryRows.size()+rowNum -1), 0, lastCol-1);
            sheet.addMergedRegion(rang);
        }
    }
    public static void setHeaderRow(String title ,List<?> queryRows ,List<?> headerRows,Workbook wb ,Sheet sheet){
        if(headerRows!=null && headerRows.size() > 0 ){
            int rowNum  = getStartRow(title,queryRows,null,null,null);
                 Row row = sheet.createRow(rowNum);
                Iterables.forEach(headerRows,(index,item) -> {
                    row.createCell(index).setCellValue(String.valueOf(item));
                });
            }
    }


    private  static  int getStartRow(String title ,List<?> queryRows ,List<?> headerRow,List<?> datas,List<?> summaryRow){
           int startRow = 0;
            if(!Utils.isEmpty(title)) {
                startRow += 1;
            }
            if(queryRows!=null && queryRows.size() > 0 ){
                startRow += queryRows.size();
            }
            if(headerRow!=null && headerRow.size() > 0 ){
                startRow += 1;
            }
            if(!Utils.isEmpty(datas)){
                startRow += datas.size();
            }
            if(!Utils.isEmpty(summaryRow)){
                startRow += 1;
            }
        return startRow;
    }

    public static  void setDatas(String title ,List<?> queryRows ,List<?> headerRow,List<?> datas,Workbook wb ,Sheet sheet){
         int startRow = getStartRow(title,queryRows,headerRow,null,null);
        for (int i = 0;  i< datas.size(); i++) {
            insertRow(sheet.createRow(i+ startRow),(List<?>)datas.get(i));
        }
    }
    public static  void setSummaryRow(String title ,List<?> queryRows ,List<?> headerRow,List<?> datas,List<?> summaryRow, Workbook wb , Sheet sheet){
        if(!Utils.isEmpty(summaryRow)){
            int startRow = getStartRow(title,queryRows,headerRow,datas,null);
            insertRow(sheet.createRow(startRow),summaryRow);
        }
    }

    public static   Workbook createExcel(String title,String sheetName ,List<?> queryRows ,List<?> headerRow,List<?> datas,List<?> summaryRow){
        Workbook workbook = new HSSFWorkbook();
         Sheet sheet  = workbook.createSheet(sheetName);
        setTitle(title,workbook,sheet,headerRow.size());
        setQueryRows(title,queryRows,workbook,sheet,headerRow.size());
        setHeaderRow(title,queryRows,headerRow,workbook,sheet);
        setDatas(title,queryRows,headerRow,datas,workbook,sheet);
        setSummaryRow(title,queryRows,headerRow,datas,summaryRow,workbook,sheet);
        return workbook;
    }

}
