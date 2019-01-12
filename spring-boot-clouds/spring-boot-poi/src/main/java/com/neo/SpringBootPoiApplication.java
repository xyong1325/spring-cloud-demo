package com.neo;

import com.neo.util.POIUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;

/*@SpringBootApplication*/
public class SpringBootPoiApplication {

	public static void main(String[] args) throws  Exception {

		/*SpringApplication.run(SpringBootPoiApplication.class, args);*/
		String fileUrl = "C:/物料入库导入模板-2019-01-11.xlsx";

		Workbook  workbook =  POIUtils.openWorkbook("C:/物料入库导入模板-2019-01-11.xlsx");
		Sheet sheet = workbook.getSheetAt(0);
		Row row = sheet.getRow(2);
		System.out.println(row.getCell(0));

 	}

}

