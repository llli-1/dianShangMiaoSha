package com.miaoshaproject.miaosha.DataObject;

public class UserPasswordDao {
    private Integer id;

    private String encretPassword;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEncretPassword() {
        return encretPassword;
    }

    public void setEncretPassword(String encretPassword) {
        this.encretPassword = encretPassword == null ? null : encretPassword.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}