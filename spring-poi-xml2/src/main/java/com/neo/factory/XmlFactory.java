package com.neo.factory;

import com.neo.common.POIUtil;
import com.neo.common.XmlUtil;
import com.neo.domain.GenEntity;
import com.neo.domain.LogisticsHead;
import com.neo.support.ConvertLogisticsHead;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *  生成 XML 公共类
 *  该类 代码存在优化空间， 暂不处理
 */
public class XmlFactory {
    public static  void GenXml(String excelPath,final String xmlDir)throws  Exception{
        XmlFactory  xmlFactory  = new XmlFactory();
        xmlFactory.excelToXml(excelPath,xmlDir);
    }
     private void excelToXml(String excelPath,final String xmlDir) throws  Exception{
         List<List<LogisticsHead>>  logisticsHeads = this.getLogisticsHeadList(excelPath);
         logisticsHeads.forEach(item ->{
             generateXml(xmlDir,new GenEntity(item));
         });
     }


    private   void generateXml(String xmlDir, GenEntity genEntity) {
        Document doc = new XmlUtil().CreateDocument(genEntity);
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        format.setNewLineAfterDeclaration(false);
        XMLWriter output = null ;
        try {
            FileOutputStream fos = new FileOutputStream(xmlDir+"/"+genEntity.getGuid()+".xml");
            output = new XMLWriter(fos, format);
            output.write(doc);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }



    /**
     * 处理 excel 的数据
     * @param excelPath
     * @return
     * @throws Exception
     */
     private List<List<LogisticsHead>> getLogisticsHeadList(String excelPath) throws  Exception{
         List<LogisticsHead>  logisticsHeads  = new ConvertLogisticsHead().convert( new POIUtil().readExcel(excelPath));
         List<List<LogisticsHead>> lst = new ArrayList<List<LogisticsHead>>();
         Map<String,LogisticsHead> logisticsHeadMap = new HashMap<String,LogisticsHead>();
         logisticsHeads.forEach(item -> logisticsHeadMap.put(item.getOrderNo(),item));
         List<LogisticsHead> newList  = new ArrayList<>(logisticsHeadMap.values());
         List<LogisticsHead> logisticsHeads2  = new ArrayList<>();
         if(newList.size()>100){
             for(int i = 0 ; i < newList.size();i++ ){
                 if( i== 100){ // 101 就新建一个数组
                     lst.add(logisticsHeads2);
                     logisticsHeads2 = new ArrayList<>();
                 }
                 logisticsHeads2.add(newList.get(i));
             }
             lst.add(logisticsHeads2);
         }else {
             lst.add(newList);
         }
        return  lst;
     }
}
