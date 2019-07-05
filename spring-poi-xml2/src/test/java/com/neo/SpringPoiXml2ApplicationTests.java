package com.neo;

import com.neo.common.POIUtil;
import com.neo.factory.XmlFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringPoiXml2ApplicationTests {

    @Test
    public void contextLoads() {

    }

    @Test
    public void genXml() throws Exception{
         String excelPath = "C:/excel/xg.xls";
         String xmlDir = "C:/excel";
        XmlFactory.GenXml(excelPath,xmlDir);
    }
}
