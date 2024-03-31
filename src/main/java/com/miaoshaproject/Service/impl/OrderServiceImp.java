package com.miaoshaproject.Service.impl;

import com.miaoshaproject.Dao.OrderDOMapper;
import com.miaoshaproject.Dao.SequenceDOMapper;
import com.miaoshaproject.DataObject.OrderDO;
import com.miaoshaproject.DataObject.SequenceDO;
import com.miaoshaproject.Error.BusinessException;
import com.miaoshaproject.Error.EmBusinessError;
import com.miaoshaproject.Service.ItemService;
import com.miaoshaproject.Service.OrderService;
import com.miaoshaproject.Service.UserService;
import com.miaoshaproject.Service.model.ItemModel;
import com.miaoshaproject.Service.model.OrderModel;
import com.miaoshaproject.Service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImp implements OrderService {

    private final ItemService itemService;
    private final UserService userService;
    private final OrderDOMapper orderDOMapper;
    private final SequenceDOMapper sequenceDOMapper;


    public OrderServiceImp(ItemService itemService, UserService userService,
                           OrderDOMapper orderDOMapper, SequenceDOMapper sequenceDOMapper) {
        this.itemService = itemService;
        this.userService = userService;
        this.orderDOMapper = orderDOMapper;
        this.sequenceDOMapper = sequenceDOMapper;

    }

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException {
        // 校验下单状态-商品是否存在、用户是否合法、购买数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品信息不存在");
        }
        UserModel userModel = userService.getUserById(userId);
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户信息不存在");
        }
        if(amount <= 0 || amount > 99){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"购买数量信息不正确");
        }
        if(promoId != null){
            // 校验对应活动是否存在于这个适用商品
            if(promoId.intValue() != itemModel.getPromoModel().getId()){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息不正确");
            }
            else if(itemModel.getPromoModel().getStatus() != 2){ // 校验活动是否正在进行中
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息不正确");
            }

        }

        // 落单就减库存-createOrder的时候就锁库存
        boolean result = itemService.decreaseStock(itemId, amount);
        if(!result){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        // 订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setPromoId(promoId);
        if(promoId != null){
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        }
        else{
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(BigDecimal.valueOf(amount)));

        // 生成交易流水号
        orderModel.setId(generateOrderId());
        OrderDO orderDO = convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);
        // 返回前端
        itemService.increaseSales(itemId, amount);
        return orderModel;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected String generateOrderId(){
        // 订单号有16位
        StringBuilder stringBuilder = new StringBuilder();
        // 前八位位时间信息
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);
        // 中间6位为自增序列 -- sequence可能超出->变成循环的
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        int sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue() + sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        String sequenceStr = String.valueOf(sequence);
        for (int i = 0; i < 8 - sequenceStr.length(); i++){
            stringBuilder.append(0);
        }
        // 最后两位为分库分表位 --暂时写死
        stringBuilder.append("00");
        return stringBuilder.toString();
    }
    private OrderDO convertFromOrderModel(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderDO;
    }
}
