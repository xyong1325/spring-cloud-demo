package com.neo.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public  static boolean  isEmpty(List list){
        return  null == list || list.size() < 1 ;
    }
    public  static boolean  isEmpty(String file){
        return file == null || "".equals(file);
    }

    public static List<?> asList(String [] rows){
        if(rows == null || rows.length <1 ){
            return new ArrayList<>();
        }else{
            return Arrays.asList(rows);
        }
    }

}
