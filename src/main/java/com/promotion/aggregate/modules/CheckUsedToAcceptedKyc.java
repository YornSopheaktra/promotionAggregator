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

@Modules("check_used_to_accepted_kyc")
@Service
public class CheckUsedToAcceptedKyc extends AbstractPromoAggregatorService {

    @Autowired
    private RedisService redisService;
    @Override
    protected Object checkProcess(RequestDTO req, PromotionShareData shareData) {
        try{
            JSONObject requestUserProfile = new JSONObject();
            requestUserProfile.put("requestGateWay",req.getRequestGateWay());
            requestUserProfile.put("serviceId","account_kyc_used_to_accept");
            requestUserProfile.put("accountId",req.getAccountId());
            String url = BeanUtils.getBean(RedisService.class).get("get_user_account_profile_staging_url");
            Debug.debugObject("check_used_to_accepted_kyc Request", requestUserProfile);

            SendRequest request = new SendRequest(url,requestUserProfile);
            JSONObject resUserProfile = request.SendRequest();
            JSONObject resData= resUserProfile.getJSONObject("data");
            Debug.debugObject("check_kyc_status Response", resData);
            return ((resData.has("kycUsedToAccepted") && !resData.isNull("kycUsedToAccepted"))) ? resData.getString("kycUsedToAccepted") : null;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
