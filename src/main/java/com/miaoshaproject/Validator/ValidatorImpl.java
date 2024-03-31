package com.miaoshaproject.Validator;

import org.springframework.beans.factory.InitializingBean;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import org.springframework.stereotype.Component;

import java.util.Set;


@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    // 校验并返回校验结果
    public ValidationResult validate(Object bean){
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if(!constraintViolationSet.isEmpty()){
            result.setHasErrors(true);
            constraintViolationSet.forEach(constraintViolation ->{
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                result.getErrorMsgMap().put(propertyName, errMsg);
            });
        }

        return result;
    }

    // 执行初始化任务、和bean有关
    @Override
    public void afterPropertiesSet(){
        // this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.validator = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory()
                .getValidator();
    }
}
