package com.neo;

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
import java.util.Date;

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


		String filePath="d:/test.xls";//文件路径
		HSSFWorkbook workbook = new HSSFWorkbook();//创建Excel文件(Workbook)
		HSSFSheet sheet  = workbook.createSheet("heb");

		HSSFRow   row   = sheet.createRow(0);
		HSSFCell cell =  	   row.createCell(0);
		         cell.setCellValue("合并单元格");
		HSSFCellStyle  cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);


		//字号

		HSSFFont font = workbook.createFont();
		font.setFontName("华文行楷");//设置字体名称
		font.setFontHeightInPoints((short)16);//设置字号
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  //粗体显示
		cellStyle.setFont(font);



		    cell.setCellStyle(cellStyle);
			CellRangeAddress rang = new CellRangeAddress(0,0,0,5);
			sheet.addMergedRegion(rang);


		 String cnt ="\r\n";
		 StringBuilder content =  new StringBuilder("门店：全部").append(cnt).append("支付方式：全部").append(cnt).append("本次搜索条件：全部");
  		    row   = sheet.createRow(1);
			cell =  	   row.createCell(0);

			cellStyle=workbook.createCellStyle();
			cellStyle.setWrapText(true);
			cell.setCellStyle(cellStyle);

	    	cell.setCellValue(new HSSFRichTextString(content.toString()));;
	     	rang = new CellRangeAddress(1,3,0,5);
		    sheet.addMergedRegion(rang);












				sheet = workbook.createSheet("Test");//创建工作表(Sheet)
		  row = sheet.createRow(0);
		 cell =       row.createCell(0);
		cell.setCellValue("xy");
		row.createCell(1).setCellValue(false);
		row.createCell(2).setCellValue( new Date());
		row.createCell(3).setCellValue( 123);






		FileOutputStream out = new FileOutputStream(filePath);
		workbook.write(out);//保存Excel文件
		out.close();//关闭文件流
		System.out.println("OK!");


	}


}

