package com.miaoshaproject.Controller;

import com.miaoshaproject.Error.BusinessException;
import com.miaoshaproject.Error.EmBusinessError;
import com.miaoshaproject.Response.CommonReturnType;
import com.miaoshaproject.Service.OrderService;
import com.miaoshaproject.Service.model.OrderModel;
import com.miaoshaproject.Service.model.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true", originPatterns = "*")
public class OrderController extends BaseController{

    private final OrderService orderService;
    private final HttpServletRequest httpServletRequest;

    public OrderController(OrderService orderService, HttpServletRequest httpServletRequest) {
        this.orderService = orderService;
        this.httpServletRequest = httpServletRequest;
    }

    // 封装下单请求
    @RequestMapping(value = "/createOrder",method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name="itemId")Integer itemId,
                                        @RequestParam(name="amount")Integer amount,
                                        @RequestParam(name="promoId", required = false)Integer promoId) throws BusinessException {
        var isLogin = (boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if(!isLogin){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户未登录，不能下单");
        }
        // 获取用户登录信息
        UserModel userModel = (UserModel)httpServletRequest.getSession().getAttribute("LOGIN_USER");

        OrderModel orderModel = orderService.createOrder(userModel.getId(), itemId, promoId, amount);
        return CommonReturnType.create(null);
    }
}
