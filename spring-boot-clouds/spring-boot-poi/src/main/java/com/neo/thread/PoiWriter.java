package com.neo.thread;

import com.neo.domain.SheetEntity;
import com.neo.util.POIUtils;
import com.neo.util.Utils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
public class PoiWriter implements Runnable {
    private final CountDownLatch doneSignal;
    private Integer prefixStartRow ;
    private CellStyle cellStyle;
    private Sheet sheet;
    private int start;
    private int end;
    private SheetEntity entity;

    public PoiWriter(CountDownLatch doneSignal, Sheet sheet, Integer start,Integer end,SheetEntity entity,CellStyle cellStyle,Integer prefixStartRow) {
        this.doneSignal = doneSignal;
        this.sheet = sheet;
        this.start = start;
        this.end = end;
        this.entity = entity;
        this.cellStyle =  cellStyle;
        this.prefixStartRow = prefixStartRow;
    }
    @Override
    public void run() {
            int createRowNum = this.start;
              try{
                  while(  createRowNum <= this.end ){
                     List<?> datas = (List<?>) entity.getDatas().get( (createRowNum-this.prefixStartRow-1) );
                     List newLit = datas;
                     if(!Utils.isEmpty(datas) && !Utils.isEmpty(entity.getHeaderRow()) && entity.getHeaderRow().size() > datas.size() ){
                         int size = entity.getHeaderRow().size() - datas.size();
                         if(size > 0 ){
                             newLit = new ArrayList<>(datas);
                             for(int i = 0; i< size; i ++){
                                 newLit.add("");
                             }
                         }
                     }
                     POIUtils.insertRow(cellStyle,POIUtils.getRow(sheet,(createRowNum-1)),newLit);
                     ++ createRowNum;
                 }
              }catch (Exception e ){
                  e.printStackTrace();
              } finally {
                  doneSignal.countDown();
               //   System.out.println("start: " + start + " end: " + end + " Count: " + doneSignal.getCount());
              }
    }
}
