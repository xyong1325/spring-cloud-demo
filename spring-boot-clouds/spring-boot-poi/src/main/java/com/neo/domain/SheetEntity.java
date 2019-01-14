package com.neo.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SheetEntity implements Serializable {

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

    String title ;
    String sheetName ;
    java.util.List<?> queryRows = new ArrayList<>();
    List<?> headerRow = new ArrayList<>();
    List<?> datas   = new ArrayList<>();
    List<?> summaryRow  = new ArrayList<>();

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
