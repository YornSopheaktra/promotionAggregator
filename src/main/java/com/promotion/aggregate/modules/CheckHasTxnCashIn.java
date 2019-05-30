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

@Modules("check_has_txn_cash_in")
@Service
public class CheckHasTxnCashIn extends AbstractPromoAggregatorService {

    @Autowired
    private RedisService redisService;

    @Override
    protected Object checkProcess(RequestDTO requestDTO, PromotionShareData shareData) {
        try {
            JSONObject hasCashInTxn = new JSONObject();
            hasCashInTxn.put("requestGateWay", requestDTO.getRequestGateWay());
            hasCashInTxn.put("serviceId", requestDTO.getServiceId());
            hasCashInTxn.put("serviceId","get_first_cash_in");
            hasCashInTxn.put("accountId", requestDTO.getAccountId());
            String url = BeanUtils.getBean(RedisService.class).get("get_account_filter_staging_url");
            Debug.debugObject("check_has_txn_cash_in Request", hasCashInTxn);

            SendRequest request = new SendRequest(url, hasCashInTxn);
            JSONObject resHasCashInTxn = request.SendRequest();
            JSONObject resData = resHasCashInTxn.getJSONObject("data");
            Debug.debugObject("check_has_txn_cash_in Response", resData);
            return resData.getString("hasTxn");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "N";
    }
}
