package com.promotion.aggregate.service;

import com.tmc.frmk.core.domain.request.RequestDTO;
import com.tmc.frmk.core.domain.response.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionAggregateService {

    @Autowired
    private PromotionAggregateProcessor promotionAggregateProcessor;

    private Logger log= LoggerFactory.getLogger(PromotionAggregateService.class);

    public ResponseDTO run(RequestDTO requestDTO) {
        ResponseDTO response = new ResponseDTO(requestDTO);
        response.setDataGateway(requestDTO.getRequestGateWay());
        log.info("promotionAggregateProcessor", requestDTO);
        try{
           promotionAggregateProcessor.process(requestDTO, response);
        }catch (Exception e){
            e.printStackTrace();
        }
        log.info("promotionAggregateProcessor", response);
        return response;
    }
}
