package com.miaoshaproject.Validator;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
public class ValidationResult {
    // 校验结果是否有错
    private boolean hasErrors = false;

    //存放错误信息
    private Map<String,String> errorMsgMap = new HashMap<>(); // 创建的时候初始化，否则添加元素或者访问的时候会有NullPointerException

    // 通用的通过字符串信息获取错误结果的msg方法
    public String getErrMsg(){
        return StringUtils.join(errorMsgMap.values().toArray(), ",");
    }
}
