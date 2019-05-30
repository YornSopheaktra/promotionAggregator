package com.promotion.aggregate.modules;

import com.promotion.aggregate.base.Modules;
import com.promotion.aggregate.process.AbstractPromoAggregatorService;
import com.promotion.aggregate.request.SendRequest;
import com.promotion.aggregate.service.PromotionShareData;
import com.te.common.util.Debug;
import com.tmc.frmk.core.domain.request.RequestDTO;
import com.tmc.frmk.core.service.RedisService;
import com.tmc.frmk.core.utils.BeanUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Modules("check_customer_classify")
@Service
public class CheckCusomterClassify extends AbstractPromoAggregatorService {
    @Autowired
    private RedisService redisService;
    @Override
    protected Object checkProcess(RequestDTO req, PromotionShareData shareData) {
        try{

            JSONObject customerClassify = new JSONObject();
            customerClassify.put("requestGateWay",req.getRequestGateWay());
            customerClassify.put("serviceId","get_account");
            customerClassify.put("accountId",req.getAccountId());
            String url = BeanUtils.getBean(RedisService.class).get("get_user_account_profile_staging_url");

            SendRequest request = new SendRequest(url,customerClassify);
            JSONObject resUserProfile = request.SendRequest();
            JSONObject resData= resUserProfile.getJSONObject("data");
            JSONObject userDetail = resData.getJSONObject("userDetail");
            Debug.debugObject("check_cusomter_classify", userDetail);
            shareData.conditionDetailResult.put("customer_classify",userDetail.getInt("classifyId"));
            return userDetail.getInt("classifyId");
        }catch (Exception e){
            e.printStackTrace();
        }
       return null;
    }
}
