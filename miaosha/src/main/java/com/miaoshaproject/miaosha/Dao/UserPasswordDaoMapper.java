package com.miaoshaproject.miaosha.Dao;

import com.miaoshaproject.miaosha.DataObject.UserPasswordDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPasswordDaoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPasswordDao record);

    int insertSelective(UserPasswordDao record);

    UserPasswordDao selectByPrimaryKey(Integer id);
    UserPasswordDao selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(UserPasswordDao record);

    int updateByPrimaryKey(UserPasswordDao record);
}