package com.neo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogisticsHead{
     private   String guid = UUID.randomUUID().toString().toUpperCase();
     private  String appType = "1";
     private  String appTime = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date(System.currentTimeMillis()));
     private  String appStatus = "2";
     private  String logisticsCode = "420198053D";
     private  String logisticsName = "湖北省快捷通国际货运代理有限公司";
     private  String logisticsNo;
     private  String orderNo;
     private  String freight = "0.0" ;
     private  String insuredFee = "0.0";
     private  String currency = "142";
     private  String packNo = "1";
     private  String weight;
     private  String consignee;
     private  String consigneeAddress;
     private  String consigneeTelephone;
    public LogisticsHead( String orderNo,String logisticsNo,String weight,String consignee, String consigneeAddress, String consigneeTelephone){
        this.orderNo = orderNo;
        this.logisticsNo = logisticsNo;
        this.weight = weight;
        this.consignee = consignee;
        this.consigneeAddress = consigneeAddress;
        this.consigneeTelephone  =  consigneeTelephone;
    }


}
