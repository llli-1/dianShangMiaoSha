package com.miaoshaproject.Controller.ViewObject;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ItemVO {
    private Integer id;

    private BigDecimal price;

    private Integer stock;

    private String title;

    private String description;

    private Integer sales; // 非入参

    private String imgUrl;

    private Integer promoStatus; // 商品是否在秒杀活动中

    private BigDecimal promoPrice; // 秒杀价格

    private  Integer promoId;

    private String startDate;
}
