package com.miaoshaproject.DataObject;

import lombok.Data;

@Data
public class UserPasswordDO {
    private Integer id;
    private String encretPassword;
    private Integer userId;
}