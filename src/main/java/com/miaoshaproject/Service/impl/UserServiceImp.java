package com.miaoshaproject.Service.impl;

import com.alibaba.druid.util.StringUtils;
import com.miaoshaproject.Dao.UserDaoMapper;
import com.miaoshaproject.Dao.UserPasswordDaoMapper;
import com.miaoshaproject.DataObject.UserDO;
import com.miaoshaproject.DataObject.UserPasswordDO;
import com.miaoshaproject.Error.BusinessException;
import com.miaoshaproject.Error.EmBusinessError;
import com.miaoshaproject.Service.UserService;
import com.miaoshaproject.Service.model.UserModel;
import com.miaoshaproject.Validator.ValidationResult;
import com.miaoshaproject.Validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImp implements UserService {

    private final UserDaoMapper userDaoMapper;
    private final UserPasswordDaoMapper userPasswordDaoMapper;
    private final ValidatorImpl validator;

    public UserServiceImp(UserDaoMapper userDaoMapper, UserPasswordDaoMapper userPasswordDaoMapper, ValidatorImpl validator) {
        this.userDaoMapper = userDaoMapper;
        this.userPasswordDaoMapper = userPasswordDaoMapper;
        this.validator = validator;
    }


    @Override
    public UserModel validateLogin(String telephone, String encretPassword) throws BusinessException {
        /* 参数校验 */
        // 通过手机号获取用户信息
        UserDO userDO = userDaoMapper.selectByTelephone(telephone);
        if(userDO == null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        // 比较密码
        UserPasswordDO userPasswordDO = userPasswordDaoMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO, userPasswordDO);
        if(!StringUtils.equals(encretPassword, userModel.getEncretPassword())){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }

        return userModel;
    }

    @Override
    @Transactional // 声明事务
    public void register(UserModel userModel) throws BusinessException {

//        if(userModel == null){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//        }
//        if(StringUtils.isEmpty(userModel.getName()) || userModel.getGender() == null || userModel.getAge() == null || StringUtils.isEmpty(userModel.getTelephone())){
//            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
//        }
        // validate验证代替手动添加代码判断
        ValidationResult result = validator.validate(userModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        // 实现model->dataobject的方法
        UserDO userDO = convertFromModel(userModel);
        try{
            userDaoMapper.insertSelective(userDO);
        }catch(DuplicateKeyException ex){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "手机号已注册");
        }

        userModel.setId(userDO.getId());
        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        userPasswordDaoMapper.insertSelective(userPasswordDO);
    }

    private UserPasswordDO convertPasswordFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncretPassword(userModel.getEncretPassword());
        userPasswordDO.setUserId(userModel.getId());

        return userPasswordDO;
    }

    private UserDO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);
        return userDO;
    }

    @Override
    public UserModel getUserById(Integer id){
        UserDO userDO = userDaoMapper.selectByPrimaryKey(id);
        if(userDO == null) return null; //对应用户不存在
        // 用户Id获取对应的用户加密密码信息
        UserPasswordDO userPasswordDO = userPasswordDaoMapper.selectByUserId(userDO.getId());
        return convertFromDataObject(userDO, userPasswordDO);
    }

    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){
        if(userDO == null) return null;
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        if(userPasswordDO != null){
            userModel.setEncretPassword(userPasswordDO.getEncretPassword());
        }
        return userModel;
    }
}
