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

@Modules("check_kyc_status")
@Service
public class CheckKycStatus  extends AbstractPromoAggregatorService {

    @Autowired
    private RedisService redisService;

    @Override
    protected Object checkProcess(RequestDTO req, PromotionShareData shareData) {
        try{
            JSONObject requestUserProfile = new JSONObject();
            requestUserProfile.put("requestGateWay",req.getRequestGateWay());
            requestUserProfile.put("serviceId","get_account");
            requestUserProfile.put("accountId",req.getAccountId());
            String url = BeanUtils.getBean(RedisService.class).get("get_user_account_profile_staging_url");
            Debug.debugObject("check_kyc_status Request", requestUserProfile);

            SendRequest request = new SendRequest(url,requestUserProfile);
            JSONObject resUserProfile = request.SendRequest();
            JSONObject resData= resUserProfile.getJSONObject("data");
            JSONObject userDetail = resData.getJSONObject("userDetail");
            Debug.debugObject("check_kyc_status Response", userDetail);
            return ((userDetail.has("kycStatus") && !userDetail.isNull("kycStatus"))) ? userDetail.getString("kycStatus") : null;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
