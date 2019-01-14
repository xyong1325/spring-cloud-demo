package com.neo.service;

import com.neo.domain.SheetEntity;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface IPOIService {

    public Workbook createExcel(String title, String sheetName , List<?> queryRows , List<?> headerRow, List<?> datas, List<?> summaryRow);

/*

    public    Workbook createExcel(List<SheetEntity> sheetParams);*/

    public byte[] createExcels(String title, String sheetName, List<?> queryRows, List<?> headerRow, List<?> datas, List<?> summaryRow);
}
