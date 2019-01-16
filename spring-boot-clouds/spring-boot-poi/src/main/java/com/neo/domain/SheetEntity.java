package com.neo.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SheetEntity implements Serializable {

  private  String title ;
  private  String  fontName = "宋体";
  private  Integer titleFontSize  = 16;
  private  String  sheetName ;
  private  List<?> queryRows = new ArrayList<>();
  private  List<?> headerRow = new ArrayList<>();

  private Integer queryFontSize   = 11;
  private Integer dataFontSize    = 11;
  private Integer summaryFontSize = 12;
  private List<?>  headerRowCellWidths = new ArrayList<>();
  private List<?> datas   = new ArrayList<>();
  private List<?> summaryRow  = new ArrayList<>();

   public SheetEntity() {
        super();
    }

    public SheetEntity(String title, String sheetName, List<?> queryRows, List<?> headerRow, List<?> datas, List<?> summaryRow) {
        this.title = title;
        this.sheetName = sheetName;
        this.queryRows = queryRows;
        this.headerRow = headerRow;
        this.datas = datas;
        this.summaryRow = summaryRow;
    }
    public SheetEntity(String title, String sheetName, List<?> queryRows, List<?> headerRow, List<?> datas, List<?> summaryRow,List<?> headerRowCellWidths) {
        this.title = title;
        this.sheetName = sheetName;
        this.queryRows = queryRows;
        this.headerRow = headerRow;
        this.datas = datas;
        this.summaryRow = summaryRow;
        this.headerRowCellWidths = headerRowCellWidths ;
    }

    public SheetEntity(String title, String fontName, Integer titleFontSize, String sheetName, List<?> queryRows, List<?> headerRow, Integer queryFontSize, Integer dataFontSize, Integer summaryFontSize, List<?> headerRowCellWidths, List<?> datas, List<?> summaryRow) {
        this.title = title;
        this.fontName = fontName;
        this.titleFontSize = titleFontSize;
        this.sheetName = sheetName;
        this.queryRows = queryRows;
        this.headerRow = headerRow;
        this.queryFontSize = queryFontSize;
        this.dataFontSize = dataFontSize;
        this.summaryFontSize = summaryFontSize;
        this.headerRowCellWidths = headerRowCellWidths;
        this.datas = datas;
        this.summaryRow = summaryRow;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public Integer getTitleFontSize() {
        return titleFontSize;
    }

    public void setTitleFontSize(Integer titleFontSize) {
        this.titleFontSize = titleFontSize;
    }

    public Integer getQueryFontSize() {
        return queryFontSize;
    }

    public void setQueryFontSize(Integer queryFontSize) {
        this.queryFontSize = queryFontSize;
    }

    public Integer getDataFontSize() {
        return dataFontSize;
    }

    public void setDataFontSize(Integer dataFontSize) {
        this.dataFontSize = dataFontSize;
    }

    public Integer getSummaryFontSize() {
        return summaryFontSize;
    }

    public void setSummaryFontSize(Integer summaryFontSize) {
        this.summaryFontSize = summaryFontSize;
    }

    public List<?> getHeaderRowCellWidths() {
        return headerRowCellWidths;
    }

    public void setHeaderRowCellWidths(List<?> headerRowCellWidths) {
        this.headerRowCellWidths = headerRowCellWidths;
    }
    public String getTitle() {
        return title;
    }

    public String getSheetName() {
        return sheetName;
    }

    public List<?> getQueryRows() {
        return queryRows;
    }

    public List<?> getHeaderRow() {
        return headerRow;
    }

    public List<?> getDatas() {
        return datas;
    }

    public List<?> getSummaryRow() {
        return summaryRow;
    }



    public void setTitle(String title) {
        this.title = title;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public void setQueryRows(List<?> queryRows) {
        this.queryRows = queryRows;
    }

    public void setHeaderRow(List<?> headerRow) {
        this.headerRow = headerRow;
    }

    public void setDatas(List<?> datas) {
        this.datas = datas;
    }

    public void setSummaryRow(List<?> summaryRow) {
        this.summaryRow = summaryRow;
    }
}
