package com.miaoshaproject.Service;

import com.miaoshaproject.Service.model.PromoModel;
import org.springframework.stereotype.Service;

@Service
public interface PromoService {
    PromoModel getPromoByItemId(Integer itemId);
}
