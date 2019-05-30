package com.promotion.aggregate.util;

import com.promotion.aggregate.service.PromotionShareData;
import com.tmc.frmk.core.domain.request.RequestDTO;
import com.tmc.frmk.core.domain.response.ResponseDTO;
import com.tmc.frmk.core.repo.EntityRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventLog {

    @Autowired
    private EntityRepo repo;

    public void logEventResponse(Class clazz, ResponseDTO responseDTO, PromotionShareData promotionShareData){
        Logger log= LoggerFactory.getLogger(clazz);
        log.info(clazz.getName(), responseDTO);

        repo.save(promotionShareData.sysRmConditionDetails);
    }

    public void logEventRequest(Class clazz, RequestDTO requestDTO, PromotionShareData promotionShareData){
        Logger log= LoggerFactory.getLogger(clazz);
        log.info(clazz.getName(), requestDTO);
    }
}
