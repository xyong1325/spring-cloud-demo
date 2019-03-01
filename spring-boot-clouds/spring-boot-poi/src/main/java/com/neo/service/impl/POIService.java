package com.neo.service.impl;

import com.neo.cost.Const;
import com.neo.domain.SheetEntity;
import com.neo.domain.SheetVo;
import com.neo.thread.PoiWriter;
import com.neo.util.Utils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.security.auth.login.Configuration;
import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * POI service
 */
public class POIService {

    /**
     * 创建字体
     * @param workbook
     * @return
     */
    private Font  createFont(Workbook workbook ){
       return  workbook.createFont();
    }

    /**
     * 设置 边框线条
     * @param cellStyle
     */
    private void setBorderLine(CellStyle cellStyle){
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setTopBorderColor(HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.getIndex());
        cellStyle.setBottomBorderColor(HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.getIndex());
        cellStyle.setLeftBorderColor(HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.getIndex());
        cellStyle.setRightBorderColor(HSSFColor.HSSFColorPredefined.GREY_40_PERCENT.getIndex());
    }

    /**
     * 标题样式设置
     * @param cellStyle
     * @param sheet
     * @param entity
     */
    private   void  setTitle(CellStyle cellStyle ,Sheet sheet,SheetEntity entity){
        if(!Utils.isEmpty(entity.getTitle())) {
            Row row = sheet.createRow(0);
            int  end = entity.getHeaderRow().size();
            for (int i =0 ;i<end; i++){
                CellUtil.createCell(row,i,"",cellStyle);
            }
            row.getCell(0).setCellValue(entity.getTitle());
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, end-1));
        }
    }

    /**
     * 查询条件设置
     * @param cellStyle
     * @param sheet
     * @param entity
     */
    private   void setQueryRows( CellStyle cellStyle ,Sheet sheet,SheetEntity entity){
        if(entity.getQueryRows() !=null && entity.getQueryRows().size() > 0 ) {
            int rowNum  = 0;
            if(!Utils.isEmpty(entity.getTitle())) {
                rowNum = rowNum +1;
            }
            String cnt = "\r\n";
            StringBuilder content = new StringBuilder();
            entity.getQueryRows().forEach(item -> {
                content.append(item).append(cnt);
            });
            for(int i = 0; i< ( entity.getQueryRows().size()+rowNum); i ++){
                Row row = sheet.createRow(rowNum +i);
                Utils.forEach(entity.getHeaderRow(),(index, item)-> CellUtil.createCell(row,index,"",cellStyle));
            }
            sheet.getRow(rowNum).getCell(0).setCellValue(new XSSFRichTextString(content.substring(0,content.length()-2)));
            sheet.addMergedRegion(new CellRangeAddress(rowNum, (entity.getQueryRows().size()+rowNum -1), 0, entity.getHeaderRow().size()-1));
        }
    }

    /**
     * 设置 头部数据
     * @param cellStyle
     * @param sheet
     * @param entity
     */
    private Map<Integer,Integer> setHeaderRow(CellStyle cellStyle , Sheet sheet, SheetEntity entity){
        if(!Utils.isEmpty(entity.getHeaderRow())){
            int rowNum  = getStartRow(entity.getTitle(),entity.getQueryRows(),null,null,null);
            Row row = sheet.createRow(rowNum);
            Map<Integer, Integer> finalMaxWidth = new HashMap<Integer,Integer>();
            Utils.forEach(entity.getHeaderRow(),(index, item)-> {
                Cell cell =  CellUtil.createCell(row,index,String.valueOf(item),cellStyle);
                int maxWidth = cell.getStringCellValue().getBytes().length;
                  if( maxWidth < Const.DEFAULT_WIDTH){
                      maxWidth = Const.DEFAULT_WIDTH;
                  }
                  if( maxWidth >= Const.MAX_WIDTH){
                      maxWidth = Const.MAX_WIDTH;
                  }
                finalMaxWidth.put(index,maxWidth * 256);
            });
            return finalMaxWidth;
        }else {
            return null;
        }

    }

    /**
     * 设置列宽
     * @param sheet
     * @param entity
     */
    private void autoSetColumnWidth(Sheet sheet,SheetEntity entity,Map<Integer,Integer> maxWidthMap){
        if(!Utils.isEmpty(entity.getHeaderRow())){
            Utils.forEach(entity.getHeaderRow(),(index,item) ->{
                Integer setWidth = Const.DEFAULT_WIDTH;
                if(!Utils.isEmpty(entity.getHeaderRowCellWidths())){
                    int size = entity.getHeaderRowCellWidths().size();
                    if( index < size ){
                        Object  obj = entity.getHeaderRowCellWidths().get(index);
                        if(obj != null){
                            setWidth = ((int)obj * 256);
                        }
                    }else{
                        setWidth = maxWidthMap.get(index);
                    }
                }else{
                    setWidth = maxWidthMap.get(index);
                }
                sheet.setColumnWidth(index,setWidth + 200);
            });
        }
    }
    /**
     * 插入一行数据
     * @param cellStyle
     * @param row
     * @param datas
     */
    private   void insertRow(CellStyle cellStyle ,Row row, List<?> datas) {
        if (datas != null && datas.size() > 0) {
            Utils.forEach(datas,(index, item)-> {
                if(!Utils.isNumber(item)){
                    CellUtil.createCell(row,index,String.valueOf(item),cellStyle);
                }else{
                    Cell cell = row.createCell(index);
                    cell.setCellValue(Double.valueOf((String)item));
                    cell.setCellStyle(cellStyle);
                }
            });
        }
    }

    /**
     *  插入 excel 数据
     * @param cellStyle
     * @param sheet
     * @param entity
     */
    private   void setDatas(CellStyle cellStyle ,Sheet sheet,SheetEntity entity){
        if(!Utils.isEmpty(entity.getDatas())){
            int startRow = getStartRow(entity.getTitle(),entity.getQueryRows(),entity.getHeaderRow(),null,null);
            Utils.forEach(entity.getDatas(),(index, item)-> {
                List<?> datas = (List<?>)item;
                List newLit = datas;
                if(!Utils.isEmpty(datas) && !Utils.isEmpty(entity.getHeaderRow()) && entity.getHeaderRow().size() > datas.size() ){
                    int size = entity.getHeaderRow().size() - datas.size();
                    if(size > 0 ){
                        newLit = new ArrayList<>(datas);
                        for(int i = 0; i< size; i ++){
                            newLit.add("");
                        }
                    }
                }
                insertRow(cellStyle,sheet.createRow(startRow+index),newLit);
            });
        }
    }

    /**
     * 使用多线程 处理
     * @param cellStyle
     * @param sheet
     * @param entity
     */
    private void setDatasThread(CellStyle cellStyle ,Sheet sheet,SheetEntity entity){
        if(!Utils.isEmpty(entity.getDatas())){
            int startRow = getStartRow(entity.getTitle(),entity.getQueryRows(),entity.getHeaderRow(),null,null);

            int dataSize =  entity.getDatas().size();
            int defaultRow =  1000;
            int maxRowNum = 2;
            int totalRow = dataSize / defaultRow  ;
            if(totalRow > maxRowNum){
                totalRow = maxRowNum;
                defaultRow =  dataSize / maxRowNum;
            }
            int mod =  dataSize % defaultRow ;
            totalRow = totalRow + ( mod > 0 ? 1 : 0 );
            try{
                ExecutorService es = Executors.newCachedThreadPool();
                CountDownLatch doneSignal = new CountDownLatch(totalRow);
                for(int i = 1 ; i<=  totalRow ; i ++ ){
                    int start = ( i ==1 ? 1 : ((i- 1) * defaultRow)+1 ) ;
                    int end = i* defaultRow ;
                    if(mod > 0 && i == totalRow){
                        end = 	((i- 1) * defaultRow)+ mod;
                    }
                    es.submit(new PoiWriter(doneSignal,sheet,(start + startRow),( end +startRow),entity,cellStyle,startRow));
                    //  System.out.println(  i + " start " + (start + startRow) + "  end  "+ ( end +startRow) );
                }
                doneSignal.await();
                es.shutdown();
            }catch (Exception e){

            }
        }
    }



    /**
     * 计算 插入excl 的起始行
     * @param title
     * @param queryRows
     * @param headerRow
     * @param datas
     * @param summaryRow
     * @return
     */
    private   int getStartRow(String title ,List<?> queryRows ,List<?> headerRow,List<?> datas,List<?> summaryRow){
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
    private   Workbook createExcel(String title,String sheetName ,List<?> queryRows ,List<?> headerRow,List<?> datas,List<?> summaryRow){
        SheetEntity sheetEntity = new SheetEntity(title,sheetName,queryRows,headerRow,datas,summaryRow);
        List<SheetEntity> sheetParams = new ArrayList<>( Const.INIT_CAPACITY);
        sheetParams.add(sheetEntity);
        return this.createExcel(sheetParams);
    }

    private   Workbook createExcel(String title,String sheetName ,List<?> queryRows ,List<?> headerRow,List<?> datas,List<?> summaryRow,List<?> cellWidths){
        SheetEntity sheetEntity = new SheetEntity(title,sheetName,queryRows,headerRow,datas,summaryRow);
        sheetEntity.setHeaderRowCellWidths(cellWidths);
        List<SheetEntity> sheetParams = new ArrayList<>( Const.INIT_CAPACITY);
        sheetParams.add(sheetEntity);
        return this.createExcel(sheetParams);
    }

    private  Workbook createSXSSFWorkbook() {
        XSSFWorkbook workbook1 = new XSSFWorkbook();
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook1, 100);
      return  sxssfWorkbook ;
    }


    /**
     * 创建标题 样式
     * @param workbook
     * @param entity
     * @return
     */
    private   CellStyle createTitleCellStyle(Workbook workbook,SheetEntity entity){
        Font font = this.createFont(workbook);
        font.setFontName(entity.getFontName());
        font.setFontHeightInPoints((short)entity.getTitleFontSize().intValue());
        font.setBold(true);
        CellStyle  style  = this.createBaseCellStyle(workbook,font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
       return style;
    }

    /***
     * 创建 插入 excel 的 单元格样式
     * @param workbook
     * @param entity
     * @return
     */
    private   CellStyle createDataCellStyle(Workbook workbook,SheetEntity entity){
        Font font = this.createFont(workbook);
        font.setFontName(entity.getFontName());
        font.setFontHeightInPoints((short)entity.getDataFontSize().intValue());
        return this.createBaseCellStyle(workbook,font);
    }

    /**
     * 创建查询条件的单元格 样式
     * @param workbook
     * @param entity
     * @return
     */
    private   CellStyle createQueryCellStyle(Workbook workbook,SheetEntity entity){
        Font font = this.createFont(workbook);
        font.setFontName(entity.getFontName());
        font.setFontHeightInPoints((short)entity.getQueryFontSize().intValue());
        CellStyle cellStyle =  this.createBaseCellStyle(workbook,font);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    /**
     * 创建 公共样式
     * @param workbook
     * @param font
     * @return
     */
    private   CellStyle createBaseCellStyle(Workbook workbook,Font font){
        CellStyle  style  = workbook.createCellStyle();
     //   style.setWrapText(true);
        style.setFont(font);
        setBorderLine(style);
        return style;
    }

    /**
     *   封装 excel
     * @param sheetParams
     * @return
     */
    private    Workbook createExcel(List<SheetEntity> sheetParams) {
        Workbook workbook = null;
        if (!Utils.isEmpty(sheetParams)) {
            workbook = createSXSSFWorkbook();
            Sheet sheet = null;
            for (SheetEntity sheetParam : sheetParams) {
                //标题 样式
                CellStyle titleStyle     =  this.createTitleCellStyle(workbook,sheetParam);
                CellStyle queryRowStyle  =  this.createQueryCellStyle(workbook,sheetParam);
                CellStyle dataStyle      =  this.createDataCellStyle(workbook,sheetParam);
                if(!Utils.isEmpty(sheetParam.getSheetName())){
                    sheet = workbook.createSheet(sheetParam.getSheetName());
                }else{
                    sheet = workbook.createSheet();
                }
                setTitle(titleStyle,sheet,sheetParam);
                setQueryRows(queryRowStyle,sheet,sheetParam);
                Map<Integer,Integer>  maxWidthMap = setHeaderRow(dataStyle,sheet,sheetParam);
               setDatas(dataStyle,sheet,sheetParam);
            // setDatasThread(dataStyle,sheet,sheetParam);
                autoSetColumnWidth(sheet,sheetParam,maxWidthMap);
            }
        }
        return workbook;
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
                    String [] data = row.split(Const.SPLIT_REGEX);
                    datas.add(Utils.asList(data));
                }
                SheetEntity  sheetEntity = new SheetEntity(item.getTitle(),item.getSheetName(),Utils.asList(item.getQueryRows()),Utils.asList(item.getHeaderRow()),datas,new ArrayList<>());
                sheetEntity.setFontName(item.getFontName());
                sheetEntity.setTitleFontSize(item.getTitleFontSize());
                sheetEntity.setQueryFontSize(item.getQueryFontSize());
                sheetEntity.setDataFontSize(item.getDataFontSize());
                sheetEntity.setSummaryFontSize(item.getSummaryFontSize());
                if(item.getHeaderRowCellWidths() !=null && item.getHeaderRowCellWidths().length > 0) {
                    sheetEntity.setHeaderRowCellWidths(Arrays.asList(item.getHeaderRowCellWidths()));
                }
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

    public  Workbook demo2(String title,String sheetName,String [] queryRows,String[] columns, String[] rows,Integer[] columnsWidths) throws Exception {
        List<List<?>>  datas = new ArrayList<>();
        for (String  row : rows) {
            String [] data = row.split(Const.SPLIT_REGEX);
            datas.add(Utils.asList(data));
        }
        return this.createExcel(title,sheetName,Utils.asList(queryRows),Utils.asList(columns),datas,null,Utils.asList(columnsWidths));
    }


}
