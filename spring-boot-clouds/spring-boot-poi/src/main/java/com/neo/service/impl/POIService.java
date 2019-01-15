package com.neo.service.impl;

import com.neo.domain.SheetEntity;
import com.neo.domain.SheetVo;
import com.neo.service.IPOIService;
import com.neo.util.Iterables;
import com.neo.util.Utils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class POIService implements IPOIService {



    private  void insertRow(Row row, List<?> columns) {
        if (columns != null && columns.size() > 0) {
            Iterables.forEach(columns,(index, item)-> row.createCell(index).setCellValue(String.valueOf(item)));
        }
    }
    private  void  setFont(String fontName, int fontHeight,boolean isCenter, boolean boldWeightBold , Workbook workbook , Cell cell){
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints((short)fontHeight);
        font.setBold(boldWeightBold);
        if(isCenter) {
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
    }

    private void setBorder(XSSFCellStyle cellStyle  ){
       // cellStyle.setBorderColor();
      //  cellStyle.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, XSSFColor.toXSSFColor(Col));
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框

    }



    private      void   setTitle(String title, Workbook workbook ,Sheet sheet,int lastCol ){
        if(!Utils.isEmpty(title)) {
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue(title);
            setFont("宋体", 16, true,true, workbook, cell);
            CellRangeAddress rang = new CellRangeAddress(0, 0, 0, lastCol-1);
            sheet.addMergedRegion(rang);
        }
    }
    private   void setQueryRows(String title ,List<?> queryRows,Workbook workbook ,Sheet sheet, int lastCol){
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
            cell.setCellStyle(cellStyle);
            cell.setCellValue(new XSSFRichTextString(content.substring(0,content.length()-2)));
            CellRangeAddress rang = new CellRangeAddress(rowNum, (queryRows.size()+rowNum -1), 0, lastCol-1);
            sheet.addMergedRegion(rang);
        }
    }

    private  void setHeaderRow(String title ,List<?> queryRows ,List<?> headerRows,Workbook wb ,Sheet sheet){
        if(headerRows!=null && headerRows.size() > 0 ){
            int rowNum  = getStartRow(title,queryRows,null,null,null);
            Row row = sheet.createRow(rowNum);
            Iterables.forEach(headerRows,(index,item) -> {
                row.createCell(index).setCellValue(String.valueOf(item));
            });
        }
    }

    private    int getStartRow(String title ,List<?> queryRows ,List<?> headerRow,List<?> datas,List<?> summaryRow){
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

    private   void setDatas(String title ,List<?> queryRows ,List<?> headerRow,List<?> datas,Workbook wb ,Sheet sheet){
        if(!Utils.isEmpty(datas)){
            int startRow = getStartRow(title,queryRows,headerRow,null,null);
            for (int i = 0;  i< datas.size(); i++) {
                insertRow(sheet.createRow(i+ startRow),(List<?>)datas.get(i));
            }
        }
    }

    private   void setSummaryRow(String title ,List<?> queryRows ,List<?> headerRow,List<?> datas,List<?> summaryRow, Workbook wb , Sheet sheet){
        if(!Utils.isEmpty(summaryRow)){
            int startRow = getStartRow(title,queryRows,headerRow,datas,null);
            insertRow(sheet.createRow(startRow),summaryRow);
        }
    }

    private   Workbook createExcel(String title,String sheetName ,List<?> queryRows ,List<?> headerRow,List<?> datas,List<?> summaryRow){
        SheetEntity sheetEntity = new SheetEntity(title,sheetName,queryRows,headerRow,datas,summaryRow);
        List<SheetEntity> sheetParams = new ArrayList<>(1);
        sheetParams.add(sheetEntity);
        return this.createExcel(sheetParams);
    }

    public static Workbook createSXSSFWorkbook() {
        XSSFWorkbook workbook1 = new XSSFWorkbook();
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook1, 100);
      return  sxssfWorkbook ;
    }

    private    Workbook createExcel(List<SheetEntity> sheetParams) {
        Workbook workbook = null;
        if (!Utils.isEmpty(sheetParams)) {
            workbook = createSXSSFWorkbook();
            Sheet sheet = null;
            for (SheetEntity sheetParam : sheetParams) {
                if(!Utils.isEmpty(sheetParam.getSheetName())){
                    sheet = workbook.createSheet(sheetParam.getSheetName());
                }else{
                    sheet = workbook.createSheet();
                }
                setTitle(sheetParam.getTitle(), workbook, sheet, sheetParam.getHeaderRow().size());
                setQueryRows(sheetParam.getTitle(), sheetParam.getQueryRows(), workbook, sheet, sheetParam.getHeaderRow().size());
                setHeaderRow(sheetParam.getTitle(), sheetParam.getQueryRows(), sheetParam.getHeaderRow(), workbook, sheet);
                setDatas(sheetParam.getTitle(), sheetParam.getQueryRows(), sheetParam.getHeaderRow(), sheetParam.getDatas(), workbook, sheet);
                setSummaryRow(sheetParam.getTitle(), sheetParam.getQueryRows(), sheetParam.getHeaderRow(), sheetParam.getDatas(), sheetParam.getSummaryRow(), workbook, sheet);
            }
        }
        return workbook;
    }


    public  byte[]  createExcels(String title,String sheetName ,List<?> queryRows ,List<?> headerRow,List<?> datas,List<?> summaryRow) throws Exception{
        Workbook workbook =null;
        ByteArrayOutputStream out  = null;
        byte[] bytes = null;
        try {
            workbook = this.createExcel(title, sheetName, queryRows, headerRow, datas, summaryRow);
            out = new ByteArrayOutputStream();
            workbook.write(out);
            bytes = out.toByteArray();
        }finally {
            if( out!=null){
                out.close();;
            }
        }
      return bytes;
    }

    @Override
    public byte[] export(String[] columns, String[] rows) throws Exception {
        List<List<?>>  datas = new ArrayList<>();
        for (String  row : rows) {
            String [] data = row.split(",");
            datas.add(Utils.asList(data));
        }
        return this.createExcels("","",null,Utils.asList(columns),datas,null);
    }

    public byte[] export(String title,String[] columns, String[] rows) throws Exception {
        List<List<?>>  datas = new ArrayList<>();
        for (String  row : rows) {
            String [] data = row.split(",");
            datas.add(Utils.asList(data));
        }
        return this.createExcels(title,"",null,Utils.asList(columns),datas,null);
    }

    public byte[] export(List<SheetVo> list) throws Exception {

        Workbook workbook =null;
        ByteArrayOutputStream out  = null;
        byte[] bytes = null;
        try {
            List<SheetEntity> sheetEntities = new ArrayList<>(list.size());
            list.forEach(item ->{
                List<List<?>>  datas = new ArrayList<>();
                for (String  row : item.getDatas()) {
                    String [] data = row.split(",");
                    datas.add(Utils.asList(data));
                }
                SheetEntity  sheetEntity = new SheetEntity(item.getTitle(),item.getSheetName(),Utils.asList(item.getQueryRows()),Utils.asList(item.getHeaderRow()),datas,new ArrayList<>());
                sheetEntities.add(sheetEntity);
            });
            workbook = this.createExcel(sheetEntities);
            out = new ByteArrayOutputStream();
            workbook.write(out);
            bytes = out.toByteArray();
        }finally {
            if( out!=null){
                out.close();;
            }
        }
        return bytes;
    }


    public byte[] export(String title,String sheetName,String[] columns, String[] rows) throws Exception {
        List<List<?>>  datas = new ArrayList<>();
        for (String  row : rows) {
            String [] data = row.split(",");
            datas.add(Utils.asList(data));
        }
        return this.createExcels(title,sheetName,null,Utils.asList(columns),datas,null);
    }

    public byte[] export(String title,String sheetName,String [] queryRows,String[] columns, String[] rows) throws Exception {
        List<List<?>>  datas = new ArrayList<>();
        for (String  row : rows) {
            String [] data = row.split(",");
            datas.add(Utils.asList(data));
        }
        return this.createExcels(title,sheetName,Utils.asList(queryRows),Utils.asList(columns),datas,null);
    }



    public byte[] export(String title, String sheetName, String[] queryRows,  String[] headerRow, String[] rows, String[] summaryRow) throws Exception {
        List<List<?>>  datas = new ArrayList<>();
        for (String row : rows) {
            String [] data = row.split(",");
            datas.add(Utils.asList(data));
        };
        return this.createExcels(title,sheetName,Utils.asList(queryRows),Utils.asList(headerRow),datas,Utils.asList(summaryRow));

    }
    @Override
    public byte[] export(String title, String sheetName, String[] queryRows,  String[] headerRow, String[][] rows, String[] summaryRow) throws Exception {
        List<List<?>>  datas = new ArrayList<>();
        for (String[] row : rows) {
            datas.add(Utils.asList(row));
        };
        return this.createExcels(title,sheetName,Utils.asList(queryRows),Utils.asList(headerRow),datas,Utils.asList(summaryRow));

    }

    @Override
    public  Workbook demo2(String[] columns, String[] rows) throws Exception {
        List<List<?>>  datas = new ArrayList<>();
        for (String  row : rows) {
            String [] data = row.split(",");
            datas.add(Utils.asList(data));
        }
        return this.createExcel("","",null,Utils.asList(columns),datas,null);
    }

    public  Workbook demo2(String title,String[] columns, String[] rows) throws Exception {
        List<List<?>>  datas = new ArrayList<>();
        for (String  row : rows) {
            String [] data = row.split(",");
            datas.add(Utils.asList(data));
        }
        return this.createExcel(title,"",null,Utils.asList(columns),datas,null);
    }
    public  Workbook demo2(String title,String sheetName,String[] columns, String[] rows) throws Exception {
        List<List<?>>  datas = new ArrayList<>();
        for (String  row : rows) {
            String [] data = row.split(",");
            datas.add(Utils.asList(data));
        }
        return this.createExcel(title,sheetName,null,Utils.asList(columns),datas,null);
    }
    public  Workbook demo2(String title,String sheetName,String [] queryRows,String[] columns, String[] rows) throws Exception {
        List<List<?>>  datas = new ArrayList<>();
        for (String  row : rows) {
            String [] data = row.split(",");
            datas.add(Utils.asList(data));
        }
        return this.createExcel(title,sheetName,Utils.asList(queryRows),Utils.asList(columns),datas,null);
    }

  /*  public Workbook demo(String[] columns, String[][] rows) throws Exception {
        List<List<?>>  datas = new ArrayList<>();
        for (String[] row : rows) {
            datas.add(Utils.asList(row));
        }
       return this.createExcel("","",null,Utils.asList(columns),datas,null);
    }

    public  Workbook demo2(String[] columns, String[] rows) throws Exception {
            List<List<?>>  datas = new ArrayList<>();
            for (String  row : rows) {
                String [] data = row.split(",");
                datas.add(Utils.asList(data));
            }
        return this.createExcel("","",null,Utils.asList(columns),datas,null);
    }*/
}
