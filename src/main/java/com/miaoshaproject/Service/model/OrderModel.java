package com.miaoshaproject.Service.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderModel {
    // 交易单号
    private String id;
    // 用户id
    private Integer userId;
    // 商品id
    private Integer itemId;
    // 购买数量
    private Integer amount;
    // 购买金额
    private BigDecimal orderPrice;
    // 购买单价
    private BigDecimal itemPrice;
    private Integer promoId;
}
