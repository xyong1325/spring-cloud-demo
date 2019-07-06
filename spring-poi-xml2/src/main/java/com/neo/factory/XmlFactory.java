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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 *  生成 XML 公共类
 *  该类 代码存在优化空间， 暂不处理
 */
public class XmlFactory {

    /**
     * @param excelPath excel 路径
     * @param xmlDir    生成的xml 的文件夹路径
     * @throws Exception
     */
    public static void GenXml(String excelPath, final String xmlDir) throws Exception {
        XmlFactory xmlFactory = new XmlFactory();
        xmlFactory.excelToXml(excelPath, xmlDir);
    }

    private void excelToXml(String excelPath, final String xmlDir) throws Exception {
        List<List<LogisticsHead>> logisticsHeads = this.getLogisticsHeadList2(excelPath);
        logisticsHeads.forEach(item -> {
            generateXml(xmlDir, new GenEntity(item));
        });
    }


    private void generateXml(String xmlDir, GenEntity genEntity) {
        Document doc = new XmlUtil().CreateDocument(genEntity);
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        format.setNewLineAfterDeclaration(false);
        XMLWriter output = null;
        try {
            FileOutputStream fos = new FileOutputStream(xmlDir + "/" + genEntity.getCurrentTimeStr() + genEntity.getGuid() + ".xml");
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
     * 切割数组
     * @param logisticsHeads  需要切割的数组
     * @param maxSize
     * @param splitNum
     * @return
     */
    private List<List<LogisticsHead>> splitList(List<LogisticsHead> logisticsHeads, int maxSize, int splitNum) {
        return Stream.iterate(0, f -> f + 1)
                .limit(maxSize)
                .parallel()
                .map(a -> logisticsHeads.parallelStream().skip(a * splitNum).limit(splitNum).collect(Collectors.toList()))
                .filter(b -> !b.isEmpty())
                .collect(Collectors.toList());
    }
    /**
     * 处理 excel 的数据
     *
     * @param excelPath
     * @return
     * @throws Exception
     */
    private List<List<LogisticsHead>> getLogisticsHeadList2(String excelPath) throws Exception {
        List<LogisticsHead> logisticsHeads = new ConvertLogisticsHead().convert(new POIUtil().readExcel(excelPath));
                            //  通过 stream 获取处理重复数据；
                            logisticsHeads = logisticsHeads.stream().collect(Collectors.collectingAndThen(
                                    Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getOrderNo()))), ArrayList::new
                            ));;
        int  splitNumber =  10 ;
        List<List<LogisticsHead>> lst = this.splitList(logisticsHeads, logisticsHeads.size(), splitNumber);
        return lst;
    }
}