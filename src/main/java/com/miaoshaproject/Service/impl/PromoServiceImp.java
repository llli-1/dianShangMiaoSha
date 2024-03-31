package com.miaoshaproject.Service.impl;

import com.miaoshaproject.Dao.PromoDOMapper;
import com.miaoshaproject.DataObject.PromoDO;
import com.miaoshaproject.Service.PromoService;
import com.miaoshaproject.Service.model.PromoModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class PromoServiceImp implements PromoService {

    private final PromoDOMapper promoDOMapper;

    public PromoServiceImp(PromoDOMapper promoDOMapper) {
        this.promoDOMapper = promoDOMapper;
    }

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);
        PromoModel promoModel = convertFromDataObject(promoDO);

        // 判断当前活动是否正在进行中
        if(promoModel == null) return null;
        LocalDateTime nowDate = LocalDateTime.now();
        var promoStartDate = promoModel.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        var promoEndtDate = promoModel.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if(nowDate.isBefore(promoStartDate)){
            promoModel.setStatus(1);
        }
        else if(nowDate.isAfter(promoEndtDate)){
            promoModel.setStatus(3);
        }
        else{
            promoModel.setStatus(2);
        }
        return promoModel;
    }
    private PromoModel convertFromDataObject(PromoDO promoDO) {
        if (promoDO == null) {
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO, promoModel);
        promoModel.setStartDate(promoDO.getStartDate());
        promoModel.setEndDate(promoDO.getEndDate());
        return promoModel;
    }
}
