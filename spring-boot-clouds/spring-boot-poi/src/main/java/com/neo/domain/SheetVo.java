package com.neo.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SheetVo implements Serializable {

    public SheetVo() {
    }

    String title ;
    String sheetName ;
    String [] queryRows ;
    String[] headerRow;
    String[] datas ;




    public SheetVo(String title, String sheetName, String[] queryRows, String[] headerRow, String[] datas) {
        this.title = title;
        this.sheetName = sheetName;
        this.queryRows = queryRows;
        this.headerRow = headerRow;
        this.datas = datas;
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
