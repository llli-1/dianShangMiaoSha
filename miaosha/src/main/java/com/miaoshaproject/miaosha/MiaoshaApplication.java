package com.miaoshaproject.miaosha;

import com.miaoshaproject.miaosha.Dao.UserDaoMapper;
import com.miaoshaproject.miaosha.DataObject.UserDao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = {"com.miaoshaproject.miaosha"})
@RestController
@MapperScan("com.miaoshaproject.miaosha.Dao")
public class MiaoshaApplication {

    @Autowired
    private UserDaoMapper userDaoMapper;

    @RequestMapping("/")
    public String home(){
        UserDao userDao = userDaoMapper.selectByPrimaryKey(1);
        if(userDao == null){
            return "用户对象不存在";
        }
        else{
            return userDao.getName();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(MiaoshaApplication.class, args);
    }

}
