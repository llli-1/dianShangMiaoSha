package com.miaoshaproject.Service.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PromoModel {
    private Integer id;
    private String promoName;
    private Date startDate;
    private Date endDate;
    private Integer itemId;
    private BigDecimal promoItemPrice;
    // 秒杀活动状态： 1-未开始；2 -进行中；3-已结束
    private Integer status;
}
