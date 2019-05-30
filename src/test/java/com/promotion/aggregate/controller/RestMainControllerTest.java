package com.promotion.aggregate.controller;

import com.te.common.util.Debug;
import com.tmc.frmk.core.domain.request.RequestDTO;
import com.tmc.frmk.core.domain.response.ResponseDTO;
import com.tmc.frmk.core.domain.response.ResponseEntity;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.LocalManagementPort;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestMainControllerTest {

    //@LocalServerPort
    int port;

    //@Autowired
    TestRestTemplate testRestTemplate;

    RequestDTO requestDTO = new RequestDTO();

    //@Before
    public void setUp(){
        requestDTO.setAccountId("10005123");
        requestDTO.setRequestGateWay("mobile");
        requestDTO.setServiceId("money_transfer");
        HashMap<String,Object> data=new HashMap<>();
        data.put("promotionType","2time_remittance");
        requestDTO.setData(data);

    }
    //@Test
    public void testPromotionAggregatorShouldReturn200(){
/*

      ResponseDTO responseDTO =  testRestTemplate.postForObject("http://localhost:"+port+"/promotion_aggregator/check_promotion",requestDTO, ResponseDTO.class);
        Debug.debugObject("responseDTO", responseDTO);
      assertThat(responseDTO.getStatus()).isEqualTo("T");


*/

    }
}
