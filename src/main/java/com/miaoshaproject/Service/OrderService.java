package com.miaoshaproject.Service;

import com.miaoshaproject.Error.BusinessException;
import com.miaoshaproject.Service.model.OrderModel;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    // 通过前端url上传过来的秒杀活动id来校验对应id是否属于对应商品且活动已经开始
    // 直接在下单接口内判断对应的商品是否存在秒杀活动，若存在进行中的则以秒杀价格下单
    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException;
}
