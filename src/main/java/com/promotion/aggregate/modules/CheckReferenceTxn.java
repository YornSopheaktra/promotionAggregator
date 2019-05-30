package com.promotion.aggregate.modules;

import com.google.gson.Gson;
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

@Modules("get_reference_txn")
@Service
public class CheckReferenceTxn extends AbstractPromoAggregatorService {
    @Autowired
    private RedisService redisService;
    @Override
    protected Object checkProcess(RequestDTO req, PromotionShareData shareData) {
        try {
            JSONObject refTxn = new JSONObject();
            refTxn.put("requestGateWay", req.getRequestGateWay());
            refTxn.put("serviceId", req.getServiceId());
            refTxn.put("accountId", req.getAccountId());
            refTxn.put("serviceId","get_user_register_data");
            String url = BeanUtils.getBean(RedisService.class).get("get_account_filter_staging_url");
            Debug.debugObject("check_reference_txn Request", refTxn);

            SendRequest request = new SendRequest(url, refTxn);
            JSONObject resRefTxn = request.SendRequest();
            JSONObject resData = resRefTxn.getJSONObject("data");
            JSONObject userRegisterByData =resData.getJSONObject("userRegisterByData");
            Debug.debugObject("check_reference_txn Response", resData);
            shareData.conditionDetailResult.put("ref_transaction_id", userRegisterByData.getInt("registerTxnId"));
            shareData.conditionDetailResult.put("agent_account__id", userRegisterByData.getInt("registerBydAccountId"));
            return userRegisterByData.getInt("registerTxnId");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
