package com.miaoshaproject.DataObject;

import lombok.Data;

@Data
public class UserDO {
    private Integer id;

    private String name;

    private Integer gender;

    private Integer age;

    private String telephone;

    private String registerMode;

    private String thirdPartyId;
}