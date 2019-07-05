package com.neo.support;

import com.neo.domain.LogisticsHead;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  数组转对象
 */
public class ConvertLogisticsHead {

    public  List<LogisticsHead> convert(List<String[]> list){
        List<LogisticsHead> logisticsHeads = null ;
        if( list!=null && list.size() > 0  ){
            logisticsHeads = list.stream().map(this::toLogisticsHead).collect(Collectors.toList());
        }
        return  logisticsHeads;
    }

     private    LogisticsHead toLogisticsHead(String[] arr){
         LogisticsHead logisticsHead  =  new LogisticsHead(arr[0],arr[13],arr[38],arr[15],arr[17],arr[16]);
         return logisticsHead;
     }

}
