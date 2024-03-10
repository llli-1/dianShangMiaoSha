package com.miaoshaproject.miaosha.Service.impl;

import com.miaoshaproject.miaosha.Dao.UserDaoMapper;
import com.miaoshaproject.miaosha.Dao.UserPasswordDaoMapper;
import com.miaoshaproject.miaosha.DataObject.UserDao;
import com.miaoshaproject.miaosha.DataObject.UserPasswordDao;
import com.miaoshaproject.miaosha.Service.UserService;
import com.miaoshaproject.miaosha.Service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    // 构造器注入代替Autowired自动注入
    private final UserDaoMapper userDaoMapper;
    private final UserPasswordDaoMapper userPasswordDaoMapper;

    public UserServiceImp(UserDaoMapper userDaoMapper, UserPasswordDaoMapper userPasswordDaoMapper) {
        this.userDaoMapper = userDaoMapper;
        this.userPasswordDaoMapper = userPasswordDaoMapper;
    }

    @Override
    public UserModel getUserById(Integer id){
        UserDao userDao = userDaoMapper.selectByPrimaryKey(id);
        if(userDao == null) return null; //对应用户不存在
        // 用户Id获取对应的用户加密密码信息
        UserPasswordDao userPasswordDao = userPasswordDaoMapper.selectByUserId(userDao.getId());
        return convertFromDataObject(userDao, userPasswordDao);
    }

    private UserModel convertFromDataObject(UserDao userDao, UserPasswordDao userPasswordDao){
        if(userDao == null) return null;

        UserModel uSerModel = new UserModel();
        BeanUtils.copyProperties(userDao,uSerModel);
        uSerModel.setEncrptPassword(userPasswordDao.getEncretPassword());
        return uSerModel;
    }
}
