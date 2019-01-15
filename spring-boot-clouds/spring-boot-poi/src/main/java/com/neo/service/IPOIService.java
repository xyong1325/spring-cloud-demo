package com.neo.service;
import org.apache.poi.ss.usermodel.Workbook;

 public interface IPOIService {

    /**
     *
     * @param columns
     * @param rows
     * @return
     * @throws Exception
     */
    public  byte[] export(String[] columns, String[] rows) throws Exception ;

   /* public byte[] export(String title, String sheetName,String[] queryRows, List<?> headerRow, String [] datas, String [] summaryRow) throws Exception ;*/

    /**
     *
     * @param title
     * @param sheetName
     * @param queryRows
     * @param headerRow
     * @param datas
     * @param summaryRow
     * @return
     * @throws Exception
     */
    public byte[] export(String title, String sheetName,String[] queryRows, String [] headerRow, String [][] datas, String [] summaryRow) throws Exception ;

    public  Workbook demo2(String[] columns, String[] rows) throws Exception ;


   /* public  Workbook demo(String[] columns, String[][] rows) throws Exception ;

    public  Workbook demo2(String[] columns, String[] rows) throws Exception ;*/

}
