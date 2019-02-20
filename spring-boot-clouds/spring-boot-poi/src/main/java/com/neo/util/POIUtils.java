package com.neo.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.usermodel.Sheet;
import java.util.List;

public class POIUtils {

    public static synchronized Row getRow(Sheet sheet, int rownum) {
        return sheet.createRow(rownum);
    }

    public  static  synchronized   void insertRow(CellStyle cellStyle , Row row, List<?> datas) {
        if (datas != null && datas.size() > 0) {
            Utils.forEach(datas,(index, item)-> {
                if(!Utils.isNumber(item)){
                    CellUtil.createCell(row,index,String.valueOf(item),cellStyle);
                }else{
                    Cell cell = row.createCell(index);
                    cell.setCellValue(Double.parseDouble((String)item));
                    cell.setCellStyle(cellStyle);
                }
            });
        }
    }
}
