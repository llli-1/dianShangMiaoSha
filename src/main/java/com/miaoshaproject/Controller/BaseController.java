package com.miaoshaproject.Controller;

import com.miaoshaproject.Error.BusinessException;
import com.miaoshaproject.Error.EmBusinessError;
import com.miaoshaproject.Response.CommonReturnType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

public class BaseController {
    public final static String CONTENT_TYPE_FORMED="application/x-www-form-urlencoded";
    // 定义exceptionHandler来解决未被controller层吸收的exception
    @ExceptionHandler(Exception.class) // 表明为异常处理器，处理所有继承自exception的异常
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody // 使得返回值可以作为响应体的形式被返回
    public  Object handlerException(HttpServletRequest request, Exception ex){
        Map<String, Object> responseData = new HashMap<>();
        if(ex instanceof BusinessException businessException){
            responseData.put("errCode", businessException.getErrorCode());
            responseData.put("errMsg", businessException.getErrMsg());
        }
        else{
            responseData.put("errCode", EmBusinessError.UNKNOW_ERROR.getErrorCode());
            responseData.put("errMsg", EmBusinessError.UNKNOW_ERROR.getErrMsg());
        }
        return CommonReturnType.create(responseData,"fail");
    }
}
