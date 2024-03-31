package com.miaoshaproject.Service.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ItemModel {

    private Integer id;

    @NotNull(message = "商品价值不能为空")
    @Min(value = 0, message = "商品价格必须大于0")
    private BigDecimal price;

    @NotNull(message = "商品库存不能为0")
    private Integer stock;

    @NotBlank(message = "商品名称不能为空")
    private String title;

    @NotBlank(message = "商品描述信息不能为空")
    private String description;

    private Integer sales; // 非入参

    @NotBlank(message = "图片信息不能为空")
    private String imgUrl;

    // 使用聚合模型
    private PromoModel promoModel;
}
