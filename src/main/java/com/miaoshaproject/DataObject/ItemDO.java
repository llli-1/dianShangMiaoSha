package com.miaoshaproject.DataObject;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ItemDO {
    private Integer id;

    private String title;

    private BigDecimal price;

    private Integer stock;

    private String description;

    private Integer sales;

    private String imgUrl;
}