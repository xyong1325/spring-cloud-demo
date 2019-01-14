package com.neo.service.impl;

import com.neo.domain.SheetEntity;
import com.neo.service.IPOIService;
import com.neo.util.Iterables;
import com.neo.util.Utils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class POIService implements IPOIService {
    public  void insertRow(Row row, List<?> columns) {
        if (columns != null && columns.size() > 0) {
            Iterables.forEach(columns,(index, item)-> row.createCell(index).setCellValue(String.valueOf(item)));
        }
    }

    public   void  setFont(String fontName, int fontHeight, boolean boldWeightBold , Workbook workbook , Cell cell){
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints((short)fontHeight);
        if(boldWeightBold) {
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
    }
    public      void   setTitle(String title, Workbook workbook ,Sheet sheet,int lastCol ){
        if(!Utils.isEmpty(title)) {
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue(title);
            setFont("华文行楷", 16, true, workbook, cell);
            CellRangeAddress rang = new CellRangeAddress(0, 0, 0, lastCol-1);
            sheet.addMergedRegion(rang);
        }
    }

    public   void setQueryRows(String title ,List<?> queryRows,Workbook workbook ,Sheet sheet, int lastCol){
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

    public  void setHeaderRow(String title ,List<?> queryRows ,List<?> headerRows,Workbook wb ,Sheet sheet){
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

    public   void setDatas(String title ,List<?> queryRows ,List<?> headerRow,List<?> datas,Workbook wb ,Sheet sheet){
        int startRow = getStartRow(title,queryRows,headerRow,null,null);
        for (int i = 0;  i< datas.size(); i++) {
            insertRow(sheet.createRow(i+ startRow),(List<?>)datas.get(i));
        }
    }
    public   void setSummaryRow(String title ,List<?> queryRows ,List<?> headerRow,List<?> datas,List<?> summaryRow, Workbook wb , Sheet sheet){
        if(!Utils.isEmpty(summaryRow)){
            int startRow = getStartRow(title,queryRows,headerRow,datas,null);
            insertRow(sheet.createRow(startRow),summaryRow);
        }
    }

    public   Workbook createExcel(String title,String sheetName ,List<?> queryRows ,List<?> headerRow,List<?> datas,List<?> summaryRow){
        SheetEntity sheetEntity = new SheetEntity(title,sheetName,queryRows,headerRow,datas,summaryRow);
        List<SheetEntity> sheetParams = new ArrayList<>(1);
        sheetParams.add(sheetEntity);
        return this.createExcel(sheetParams);
    }

    public    Workbook createExcel(List<SheetEntity> sheetParams) {
        Workbook workbook = null;
        if (!Utils.isEmpty(sheetParams)) {
            workbook = new HSSFWorkbook();
            Sheet sheet = null;
            for (SheetEntity sheetParam : sheetParams) {
                sheet = workbook.createSheet(sheetParam.getSheetName());
                setTitle(sheetParam.getTitle(), workbook, sheet, sheetParam.getHeaderRow().size());
                setQueryRows(sheetParam.getTitle(), sheetParam.getQueryRows(), workbook, sheet, sheetParam.getHeaderRow().size());
                setHeaderRow(sheetParam.getTitle(), sheetParam.getQueryRows(), sheetParam.getHeaderRow(), workbook, sheet);
                setDatas(sheetParam.getTitle(), sheetParam.getQueryRows(), sheetParam.getHeaderRow(), sheetParam.getDatas(), workbook, sheet);
                setSummaryRow(sheetParam.getTitle(), sheetParam.getQueryRows(), sheetParam.getHeaderRow(), sheetParam.getDatas(), sheetParam.getSummaryRow(), workbook, sheet);
            }
        }
        return workbook;
    }
    public   byte[]  createExcels(String title,String sheetName ,List<?> queryRows ,List<?> headerRow,List<?> datas,List<?> summaryRow){
      return null;
    }


}
