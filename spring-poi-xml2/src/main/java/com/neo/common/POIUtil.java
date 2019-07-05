package com.neo.common;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * excel读写工具类 */
public class POIUtil {
    private final static String xls = "xls";
    private final static String xlsx = "xlsx";
    public  List<String[]> readExcel(File file) throws Exception{
        checkFile(file);

        Workbook workbook = getWorkBook(file);
        List<String[]> list = new ArrayList<String[]>();
        if(workbook != null){
            Sheet sheet = workbook.getSheetAt(0);
            int firstRowNum  = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();
            int lastCellNum = sheet.getRow(0).getLastCellNum();
            for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){
                Row row = sheet.getRow(rowNum);
                if(row == null){
                    continue;
                }
                int firstCellNum = 0;
                if(getCellValue(row.getCell(firstCellNum)).equals("")){
                    continue;
                }
                String[] cells = new String[lastCellNum];
                //循环当前行
                for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                    Cell cell = row.getCell(cellNum);
                    if( cell == null){
                        cells[cellNum] = "";
                    }else {
                       try{
                           cells[cellNum] = getCellValue(cell);
                       }catch (Exception e){
                           e.fillInStackTrace();
                       }
                    }
                }
                list.add(cells);
            }
            workbook.close();
        }
        return list;
    }
    public  List<String[]> readExcel(String filePath)throws Exception{
        return  this.readExcel(new File(filePath));
    }
    private  void checkFile(File file) throws IOException{
        if(null == file){
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getName();
        //判断文件是否是excel文件
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            throw new IOException(fileName + "不是excel文件");
        }
    }
    private  Workbook getWorkBook(File file) throws  Exception {
        return  createSXSSFWorkbook(file);
    }
    private  Workbook createSXSSFWorkbook(File file) throws  Exception {
        Workbook workbook = null ;
        if(file.getName().endsWith(xls)) {
            workbook = new HSSFWorkbook(new FileInputStream(file));
        }else{
            workbook = new XSSFWorkbook(file);
        }
        return  workbook ;
    }
    private  String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        if(cell.getCellType() == CellType.NUMERIC){
            cell.setCellType(CellType.STRING);
        }
        switch (cell.getCellType()){
            case  NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BLANK: //空值
                cellValue = "";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue.trim();
    }
}