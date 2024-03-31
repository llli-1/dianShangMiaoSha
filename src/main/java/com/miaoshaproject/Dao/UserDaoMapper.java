package com.miaoshaproject.Dao;

import com.miaoshaproject.DataObject.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDaoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(Integer id);

    UserDO selectByTelephone(String telephone);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);
}