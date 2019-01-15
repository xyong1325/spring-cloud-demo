package com.neo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neo.domain.SheetVo;
import com.neo.service.impl.POIService;

import java.util.List;

public class ExcelExport {

    public static byte[] export(String[] columns, String[] rows) throws Exception {
        return new POIService().export(columns,rows);
    }
    public static byte[] export(String title,String[] columns, String[] rows) throws Exception {
        return new POIService().export(title,columns,rows);
    }
    public static byte[] export(String title,String sheetName,String[] columns, String[] rows) throws Exception {
        return new POIService().export(title,sheetName,columns,rows);
    }
    public static byte[] export(String sheetParamsJson) throws Exception {
        List<SheetVo> list = JSON.parseArray(sheetParamsJson,SheetVo.class);
        return new POIService().export( list);
    }


}
