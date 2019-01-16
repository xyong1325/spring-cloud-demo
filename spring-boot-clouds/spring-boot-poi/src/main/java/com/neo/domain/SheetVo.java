package com.neo.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SheetVo implements Serializable {
 private  String title ;
 private  String sheetName ;
 private  String [] queryRows ;
 private  String[] headerRow;
 private  String[] datas ;
 private  String  fontName = "宋体";
 private  Integer titleFontSize  = 16;
 private  Integer queryFontSize   = 11;
 private Integer dataFontSize    = 11;
 private Integer summaryFontSize = 12;
 private Integer []  headerRowCellWidths ;

    public SheetVo() {

    }

    public SheetVo(String title, String sheetName, String[] queryRows, String[] headerRow, String[] datas) {
        this.title = title;
        this.sheetName = sheetName;
        this.queryRows = queryRows;
        this.headerRow = headerRow;
        this.datas = datas;

    }

    public SheetVo(String title, String sheetName, String[] queryRows, String[] headerRow, String[] datas, String fontName, Integer titleFontSize, Integer queryFontSize, Integer dataFontSize, Integer summaryFontSize, Integer[] headerRowCellWidths) {
        this.title = title;
        this.sheetName = sheetName;
        this.queryRows = queryRows;
        this.headerRow = headerRow;
        this.datas = datas;
        this.fontName = fontName;
        this.titleFontSize = titleFontSize;
        this.queryFontSize = queryFontSize;
        this.dataFontSize = dataFontSize;
        this.summaryFontSize = summaryFontSize;
        this.headerRowCellWidths = headerRowCellWidths;
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

    public Integer[] getHeaderRowCellWidths() {
        return headerRowCellWidths;
    }

    public void setHeaderRowCellWidths(Integer[] headerRowCellWidths) {
        this.headerRowCellWidths = headerRowCellWidths;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String[] getQueryRows() {
        return queryRows;
    }

    public void setQueryRows(String[] queryRows) {
        this.queryRows = queryRows;
    }

    public String[] getHeaderRow() {
        return headerRow;
    }

    public void setHeaderRow(String[] headerRow) {
        this.headerRow = headerRow;
    }

    public String[] getDatas() {
        return datas;
    }

    public void setDatas(String[] datas) {
        this.datas = datas;
    }
}
