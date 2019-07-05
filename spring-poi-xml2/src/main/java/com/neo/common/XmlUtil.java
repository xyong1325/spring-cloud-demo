package com.neo.common;

import com.neo.domain.GenEntity;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 *  还有部分 代码未抽取 公共，需要优化
 * xml 公共方法
 */
public class XmlUtil {

    /**
     * 生成 xml tree
     * @param genEntity
     * @return
     */
    public Document CreateDocument(GenEntity genEntity){
        Document document = DocumentHelper.createDocument();
        Element rootElement = createRootElement(document,genEntity);
        setLogisticsElement(rootElement,genEntity);
        setBaseTransferElement(rootElement,genEntity);
        return  document;
    }

    /**
     *
     * @param document
     * @param genEntity
     * @return
     */
    private Element createRootElement(Document document, GenEntity genEntity){
        Element rootElement =     document.addElement("ceb:CEB511Message")
                .addAttribute("guid",genEntity.getGuid())
                .addAttribute("version","1.0")
                .addAttribute("xmlns:ceb", "http://www.chinaport.gov.cn/ceb")
                .addAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance")
                .addNamespace("ceb", "http://www.chinaport.gov.cn/ceb");
        return  rootElement;
    }

    private void setLogisticsElement( Element rootElement,GenEntity genEntity){
        if( genEntity.getLogisticsHeads() !=null && genEntity.getLogisticsHeads().size() > 0){
            genEntity.getLogisticsHeads().forEach(item  ->{
                Element Logistics = rootElement.addElement("ceb:Logistics");
                Element LogisticsHead = Logistics.addElement("ceb:LogisticsHead");
                LogisticsHead.addElement("ceb:consignee").setText(item.getConsignee());
                LogisticsHead.addElement("ceb:consigneeAddress").addCDATA(item.getConsigneeAddress());
                LogisticsHead.addElement("ceb:consigneeTelephone").setText(item.getConsigneeTelephone());
                LogisticsHead.addElement("ceb:weight").setText(item.getWeight());
                LogisticsHead.addElement("ceb:orderNo").setText(item.getOrderNo());
                LogisticsHead.addElement("ceb:guid").setText(item.getGuid());
                LogisticsHead.addElement("ceb:appType").setText(item.getAppType());
                LogisticsHead.addElement("ceb:appTime").setText(item.getAppTime());
                LogisticsHead.addElement("ceb:appStatus").setText(item.getAppStatus());
                LogisticsHead.addElement("ceb:logisticsCode").setText(item.getLogisticsCode());
                LogisticsHead.addElement("ceb:logisticsName").setText(item.getLogisticsName());
                LogisticsHead.addElement("ceb:logisticsNo").setText(item.getLogisticsNo());
                LogisticsHead.addElement("ceb:freight").setText(item.getFreight());
                LogisticsHead.addElement("ceb:insuredFee").setText(item.getInsuredFee());
                LogisticsHead.addElement("ceb:currency").setText(item.getCurrency());
                LogisticsHead.addElement("ceb:packNo").setText(item.getPackNo());
            });
        }
    }
    private void setBaseTransferElement( Element rootElement,GenEntity genEntity){
        Element BaseTransfer = rootElement.addElement("ceb:BaseTransfer");
        BaseTransfer.addElement("ceb:copCode").setText("420198053D");
        BaseTransfer.addElement("ceb:copName").setText("湖北省快捷通国际货运代理有限公司");
        BaseTransfer.addElement("ceb:dxpMode").setText("DXP");
        BaseTransfer.addElement("ceb:dxpId").setText("DXPENT0000021447");
    }
}
