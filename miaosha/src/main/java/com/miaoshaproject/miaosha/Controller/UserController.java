package com.miaoshaproject.miaosha.Controller;

import com.miaoshaproject.miaosha.Service.UserService;
import com.miaoshaproject.miaosha.Service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("user")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    @ResponseBody
    public UserModel getUser(@RequestParam(name="id") Integer id){
        UserModel userModel = userService.getUserById(id);
        return userModel;
    }
}
