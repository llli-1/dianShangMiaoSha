package com.miaoshaproject.Dao;

import com.miaoshaproject.DataObject.ItemStockDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ItemStockDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockDO record);

    int insertSelective(ItemStockDO record);

    ItemStockDO selectByPrimaryKey(Integer id);
    ItemStockDO selectByItemId(Integer item_id);

    int updateByPrimaryKeySelective(ItemStockDO record);

    int updateByPrimaryKey(ItemStockDO record);
    int decreaseStock(@Param("itemId") Integer id, @Param("amount") Integer amount);
}