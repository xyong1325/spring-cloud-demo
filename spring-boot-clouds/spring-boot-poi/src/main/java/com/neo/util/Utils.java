package com.neo.util;

import com.neo.cost.Const;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

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
    public static List<Integer> asList(Integer [] rows){
        if(rows == null || rows.length <1 ){
            return new ArrayList<>();
        }else{
            return Arrays.asList(rows);
        }
    }

    /**
     * 判断字符串 是否是数字（0开头的默认为字符串）
     * @param str
     * @return
     */
    public  static  boolean isNumber(Object str){
        boolean  bool = true;
        try{
            Double.parseDouble((String) str);
        }catch (Exception e){
            bool = false;
        }
        // 如果出现 字符串类型 为 00012 开头的字符串；默认处理为字符串
       if( bool &&  ((String) str).length() > 1 ){
           String  validNumPrefix  =  ((String) str).substring(0,2);
           if(!validNumPrefix.contains(Const.NUMBER_DOT)){
               bool = false;
           }
       }
        return bool;
    }

    public static <E> void forEach( Iterable<? extends E> elements, BiConsumer<Integer, ? super E> action) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);
        int index = 0;
        for (E element : elements) {
            action.accept(index++, element);
        }
    }

}
