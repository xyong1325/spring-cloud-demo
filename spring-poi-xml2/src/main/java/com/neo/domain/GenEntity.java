package com.neo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 数据集合
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GenEntity implements Serializable {
    private List<LogisticsHead> logisticsHeads;
    private String  currentTimeStr = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date(System.currentTimeMillis()));
    private String guid = UUID.randomUUID().toString();
    public GenEntity( List<LogisticsHead> logisticsHeads){
        this.logisticsHeads = logisticsHeads;
    }
}
