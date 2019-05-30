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

import java.util.Arrays;
import java.util.List;

@Modules("check_kyc_action_status")
@Service
public class CheckKycActionStatus extends AbstractPromoAggregatorService {

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
            Debug.debugObject("check_kyc_action_status Request: ", requestUserProfile);

            SendRequest request = new SendRequest(url,requestUserProfile);
            JSONObject resUserProfile = request.SendRequest();

            JSONObject resData= resUserProfile.getJSONObject("data");
            JSONObject userDetail = resData.getJSONObject("userDetail");
            Debug.debugObject("check_kyc_action_status Response", userDetail);
            String actionStatusKyc =((userDetail.has("actionStatus") && !userDetail.isNull("actionStatus"))) ? userDetail.getString("actionStatus") : null;

            Integer userAccountClassifyId = userDetail.getInt("classifyId");

            if(shareData.sysRmConditionDetails.getOperator().equalsIgnoreCase("in_list")){
                List<String> resultList = Arrays.asList(shareData.sysRmConditionDetails.getKeyResult().split("\\s*,\\s*"));
                if (resultList.contains(actionStatusKyc) &&  userAccountClassifyId!= 74){
                    return actionStatusKyc;
                }
            }else if(shareData.sysRmConditionDetails.getOperator().equalsIgnoreCase("=")){
                if (actionStatusKyc.equalsIgnoreCase(shareData.sysRmConditionDetails.getKeyResult()) && userAccountClassifyId !=74){
                    return actionStatusKyc;
                }
            }
        }catch ( Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
