package com.miaoshaproject.Service;

import com.miaoshaproject.Error.BusinessException;
import com.miaoshaproject.Service.model.ItemModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {
    //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;
    // 商品列表浏览
    List<ItemModel> listItem();

    // 商品详情浏览
    ItemModel getItemById(Integer id);

    // 库存扣减
    boolean decreaseStock(Integer itemId, Integer amount);
    void increaseSales(Integer itemId, Integer amount) throws BusinessException;
}
