package com.neo;

public class BinaryTest {

    public static  void main(String [] args){
        Integer  num =  53;

        String binaryStr  = "110101";
        System.out.println(Integer.toBinaryString(num));

    }


    /**
     * 十进制 转 二进制
     * @param num
     * @return
     */
    public static String toBinaryString(int num){
        return Integer.toBinaryString(num);
    }


}
