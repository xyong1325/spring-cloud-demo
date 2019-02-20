package com.neo;

import com.neo.service.impl.POIService;
import com.neo.util.Utils;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootPoiApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public  void testExportExcel() throws Exception{
		String filePath="c:/test.xlsx";//文件路径
		POIService ipoiService = new POIService();
 		String title = "梦圆皇宫";
		String sheetName = "我是sheet";
		String [] queryRows = new String[]{"门店：全部","支付方式：全部","本次搜索条件：全部","本次搜索条件99：全部99"};
		String [] queryHeaderRows = new String [] { "序号",
												"供应商ID",
												"供应商名称",
												"所在城市",
												"合作类型",
												"负责人",
												"负责人电话",
												"修改时间",
												"合约状态",
												"合约期限",
												"合约开始时间",
												"合约到期时间",
												"剩余时间"};
		int size = 10005 ;
		String [] 	datas  = new String [size] ;
		for (int i = 0 ; i< size; i++){
			// datas[i] = (i+1) +",82965,须要科技(深圳)有限公司,深圳,提供完整项目或提供商品,盘桂元,13926828235,2018-08-02,合作中,24.0个月,2017-12-27,2019-12-27,346天";
			  datas[i] = (i+1) +",82965,须要科技(深圳)有限公司,深圳,提供完整项提供完整项目或提供商品提供完整项目或提供商品提供完整项目或提供商品提供完整项目或提供商品提供完整项目或提供商品目或提供商品,盘桂元,13926828235,2018-08-02,合作中,24.0个月";
		     // datas[i] = (i+1) +",82965,,,提供完整项目或提供商品,盘桂元,13926828235,2018-08-02,合作中,24.0个月,2017-12-27";
		}
		Integer[]  columnsWidths =  new Integer[]{5,10,15,5,10,10,10,10,10};
		columnsWidths  = null ;
		long start = System.currentTimeMillis();
		Workbook workbook = ipoiService.demo2(title,sheetName,queryRows,queryHeaderRows,datas,columnsWidths);
		FileOutputStream out = new FileOutputStream(filePath);
		workbook.write(out);
		out.close();
		System.out.println("OK!");
		long  end = System.currentTimeMillis();
        int mis =  (int)(end -start)/1000 ;
		System.out.println("执行时间 "+ mis +"s" );
	}


	@Test
	public void test(){
		String  str = "A0001";
/*		Assert.assertEquals(false,Utils.isNumber(str));
		str = "00001";
		Assert.assertEquals(false,Utils.isNumber(str));
		str = "0.0001";
		Assert.assertEquals(true,Utils.isNumber(str));
		str = "1.025";
		Assert.assertEquals(true,Utils.isNumber(str));
		str = "0.000A";
		Assert.assertEquals(false,Utils.isNumber(str));*/
		str = "696.00";
		Assert.assertEquals(true,Utils.isNumber(str));
	}

	@Test
	public void test1(){
		int prefixNum = 0 ;

		 int datas =  7003;
		 int defaultRow =  1000;
		 int maxRowNum = 4;
		 int totalRow = datas / defaultRow  ;
		    if(totalRow > maxRowNum){
				totalRow = maxRowNum;
		    	defaultRow =  datas / maxRowNum;
			}

		 int mod =  datas % defaultRow ;
		     totalRow = totalRow + ( mod > 0 ? 1 : 0 );
		 for(int i = 1 ; i<=  totalRow ; i ++ ){
		 	int start = ( i ==1 ? 1 : ((i- 1) * defaultRow)+1 ) ;
		 	int end = i* defaultRow ;
		 	if(mod > 0 && i == totalRow){
				end = 	((i- 1) * defaultRow)+ mod;
			}
		 	System.out.println(  i + " start " + (start + prefixNum) + "  end  "+ ( end +prefixNum) );
		 }

	}
}

