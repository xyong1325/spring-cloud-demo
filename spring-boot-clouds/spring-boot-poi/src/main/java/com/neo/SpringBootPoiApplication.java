package com.neo;

import com.neo.service.IPOIService;
import com.neo.service.impl.POIService;
import com.neo.util.POIUtil;
import com.neo.util.POIUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellRange;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/*@SpringBootApplication*/
public class SpringBootPoiApplication {


	/*private static  void setHeader(HSSFWorkbook workbook ,HSSFCellStyle style)*/

	public static void main(String[] args) throws  Exception {

		/*SpringApplication.run(SpringBootPoiApplication.class, args);*/
	/*	String fileUrl = "C:/物料入库导入模板-2019-01-11.xlsx";

		Workbook  workbook =  POIUtils.openWorkbook("C:/物料入库导入模板-2019-01-11.xlsx");
		Sheet sheet = workbook.getSheetAt(0);
		Row row = sheet.getRow(2);
		System.out.println(row.getCell(0));*/

		List<?> data1 =	 Arrays.asList(new String[]{"1","美丽金充值卡","名称","100","300","888"});
		List<?> data2 =	 Arrays.asList(new String[]{"2","美丽金充值卡","名称","20","400","888"});
		List<?> data3 =	 Arrays.asList(new String[]{"3","美丽金充值卡","名称","30","500","888"});

		 String filePath="D:/test.xls";//文件路径
		List<?> queryRows =  Arrays.asList( new String []{"门店：全部","支付方式：全部","本次搜索条件：全部","本次搜索条件99：全部99"} ) ;
				//queryRows  = new ArrayList<>();
		List<?> headerRows =	 Arrays.asList(new String[]{"排名","美丽金充值卡","名称","销售数量（订单数）","销售金额","惠z"});

		List<?> summaryRow   =  Arrays.asList(new String[]{"汇总","","","150","1200",""});

		String title = "9999" ;
		List<List<?>> datas  = new ArrayList<>();
		datas.add(data1);
		datas.add(data2);
		datas.add(data3);

	 /*	HSSFWorkbook workbook = new HSSFWorkbook();
	  	HSSFSheet sheet  = workbook.createSheet("美丽金充值卡");
	    POIUtils.setTitle(title,workbook,sheet,headerRows.size());
	    POIUtils.setQueryRows(title,queryRows,workbook,sheet,headerRows.size());
		POIUtils.setHeaderRow(title,queryRows,headerRows,workbook,sheet);
		POIUtils.setDatas(title,queryRows,headerRows,datas,workbook,sheet);
		POIUtils.setSummaryRow(title,queryRows,headerRows,datas,summaryRow,workbook,sheet);*/
		IPOIService ipoiService = new POIService();
	    Workbook workbook = ipoiService.createExcel(title,"美丽金充值卡",queryRows,headerRows,datas,summaryRow);
		FileOutputStream out = new FileOutputStream(filePath);
		workbook.write(out);//保存Excel文件
		out.close();//关闭文件流
		System.out.println("OK!");


	}


}

