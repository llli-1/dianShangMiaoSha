package com.miaoshaproject.Service.impl;

import com.miaoshaproject.Dao.ItemDOMapper;
import com.miaoshaproject.Dao.ItemStockDOMapper;
import com.miaoshaproject.DataObject.ItemDO;
import com.miaoshaproject.DataObject.ItemStockDO;
import com.miaoshaproject.Error.BusinessException;
import com.miaoshaproject.Error.EmBusinessError;
import com.miaoshaproject.Service.ItemService;
import com.miaoshaproject.Service.PromoService;
import com.miaoshaproject.Service.model.ItemModel;
import com.miaoshaproject.Service.model.PromoModel;
import com.miaoshaproject.Validator.ValidationResult;
import com.miaoshaproject.Validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ItemServiceImp implements ItemService {

    private final ValidatorImpl validator;
    private final ItemDOMapper itemDOMapper;
    private final ItemStockDOMapper itemStockDOMapper;
    private final PromoService promoService;

    public ItemServiceImp(ValidatorImpl validator, ItemDOMapper itemDOMapper, ItemStockDOMapper itemStockDOMapper, PromoService promoService) {
        this.validator = validator;
        this.itemDOMapper = itemDOMapper;
        this.itemStockDOMapper = itemStockDOMapper;
        this.promoService = promoService;
    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) throws BusinessException{
        itemDOMapper.increaseSales(itemId, amount);
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount){
        int affectedRow = itemStockDOMapper.decreaseStock(itemId, amount); // 成功改变了是1；失败-0
        return affectedRow > 0;
    }

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        // 校验参数
        ValidationResult result = validator.validate(itemModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        // itemModel转为itemDO来操作数据库，然后得到数据库id之后再保存在itemModel里
        ItemDO itemDO = convertItemDOFromModel(itemModel);
        itemDOMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());

        ItemStockDO itemStockDO = convertItemStockDOFromItemModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);

        return this.getItemById(itemModel.getId());
    }

    public ItemDO convertItemDOFromModel(ItemModel itemModel){
        if(itemModel == null) return null;
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel, itemDO);
        return itemDO;
    }

    private ItemStockDO convertItemStockDOFromItemModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        // 不能直接copy过去，因为有类型不一样
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setItemId(itemModel.getId());
        itemStockDO.setStock(itemModel.getStock());

        return itemStockDO;
    }

    @Override
    public List<ItemModel> listItem(){
        List<ItemDO> itemDOList = itemDOMapper.listItem();
        return itemDOList.stream().map(itemDO ->{
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            return this.convertModelFromDataObject(itemDO, itemStockDO);
        }).toList();
    }

    // 商品详情浏览
    @Override
    public ItemModel getItemById(Integer id){
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if(itemDO == null) return null;
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
        // 将DO转为model
        ItemModel itemModel = convertModelFromDataObject(itemDO, itemStockDO);
        PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
        // 记录未开始或正在进行的秒杀活动
        if(promoModel != null && promoModel.getStatus() != 3){
            itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }

    private ItemModel convertModelFromDataObject(ItemDO itemDO, ItemStockDO itemStockDO){
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO, itemModel);
        itemModel.setPrice(new BigDecimal(String.valueOf(itemDO.getPrice())));
        itemModel.setStock(itemStockDO.getStock());
        return itemModel;
    }
}
