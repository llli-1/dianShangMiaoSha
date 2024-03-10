package com.miaoshaproject.miaosha.Dao;

import com.miaoshaproject.miaosha.DataObject.UserDao;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDaoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserDao record);

    int insertSelective(UserDao record);

    UserDao selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserDao record);

    int updateByPrimaryKey(UserDao record);
}