package com.miaoshaproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiaoshaApplication {

    /*private final UserDaoMapper userDaoMapper;

    public MiaoshaApplication(UserDaoMapper userDaoMapper) {
        this.userDaoMapper = userDaoMapper;
    }

    @RequestMapping("/")
    public String home(){
        UserDao userDao = userDaoMapper.selectByPrimaryKey(1);
        if(userDao == null){
            return "用户对象不存在";
        }
        else{
            return userDao.getName();
        }
    }*/

    public static void main(String[] args) {
        SpringApplication.run(MiaoshaApplication.class, args);
    }

}
