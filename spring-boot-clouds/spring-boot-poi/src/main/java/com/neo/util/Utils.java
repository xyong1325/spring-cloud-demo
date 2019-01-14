package com.neo.util;

import java.io.File;
import java.util.List;

public class Utils {

    public  static boolean  isEmpty(List list){
        return  null == list || list.size() < 1 ;
    }
    public  static boolean  isEmpty(String file){
        return file == null || "".equals(file);
    }

}
