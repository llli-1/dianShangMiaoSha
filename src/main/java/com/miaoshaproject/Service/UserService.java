package com.miaoshaproject.Service;

import com.miaoshaproject.Error.BusinessException;
import com.miaoshaproject.Service.model.UserModel;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
    UserModel validateLogin(String telephone, String encretPassword) throws BusinessException;
}
