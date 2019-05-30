package com.promotion.aggregate;

import com.promotion.aggregate.dao.MappingRulesCampaignDao;
import com.promotion.aggregate.dao.PromotionAggregateDao;
import com.promotion.aggregate.dto.PromotionEffectiveMapped;
import com.promotion.aggregate.service.PromotionProcessor;
import com.tmc.frmk.core.domain.request.RequestDTO;
import com.tmc.frmk.core.domain.response.ResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


//@RunWith(SpringRunner.class)
//@SpringBootTest
public class PromotionAggregationApplicationTests {

    //@Autowired
    private MappingRulesCampaignDao dao;

    //@Autowired
    PromotionProcessor promotionProcessor;


    //@Test
    public void shouldReturnSystemRuleOKWhenGivenValid() throws Exception{
        RequestDTO requestDTO = new RequestDTO();
        ResponseDTO responseDTO = new ResponseDTO();
        promotionProcessor.process(requestDTO,responseDTO);
    }

/*
    @Test
    public void shouldReturnSystemRuleNullWhenGivenValid() throws Exception{
        SysRuleMechanicSet sysRuleMechanicSet= promotionAggregateRepo.getMechanicRuleSetEffective("mobisdfsdfsdfle","ptu_promotion","ptu");
        Assertions.assertThat(sysRuleMechanicSet).isNull();
    }
*/

}

