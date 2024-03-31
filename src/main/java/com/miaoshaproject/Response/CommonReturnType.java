package com.miaoshaproject.Response;

import lombok.Data;

@Data
public class CommonReturnType {

    // 返回状态-success or fail
    private String status;

    // 成功-data返回前端需要的json数据
    // 失败-返回通用的错误码格式
    private Object data;

    // 通用的创建方法
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result, "success");
    }

    public static CommonReturnType create(Object result, String status){
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

}
