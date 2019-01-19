package com.neo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neo.domain.SheetVo;
import com.neo.service.impl.POIService;

import java.util.List;

/**
 * excel 导出 接口接口暴露
 */
public class ExcelExport {
    public static byte[] export(String sheetParamsJson) throws Exception {
        List<SheetVo> list = JSON.parseArray(sheetParamsJson,SheetVo.class);
        return new POIService().export( list);
    }
}
