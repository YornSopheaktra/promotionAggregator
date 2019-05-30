package com.promotion.aggregate.modules;

import com.promotion.aggregate.base.Modules;
import com.promotion.aggregate.process.AbstractPromoAggregatorService;
import com.promotion.aggregate.request.SendRequest;
import com.promotion.aggregate.service.PromotionShareData;
import com.tmc.frmk.core.domain.request.RequestDTO;
import com.tmc.frmk.core.service.RedisService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

@Modules("check_bag_reward")
public class CheckBagRewards extends AbstractPromoAggregatorService {

    @Autowired
    private RedisService redisService;

    @Override
    protected Object checkProcess(RequestDTO req, PromotionShareData shareData) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String startDateTime = format.format(shareData.promotionEffectiveMapped.getStartEffectiveDate());
        String endDateTime = format.format(shareData.promotionEffectiveMapped.getEndEffectiveDate()==null? new Date(): shareData.promotionEffectiveMapped.getEndEffectiveDate());

        System.out.println("========= startDateTime "+startDateTime);
        HashMap<String, Object> requestData = new HashMap<>();
        requestData.put("stockAccountId",shareData.promotionEffectiveMapped.getStockAccountId());
        requestData.put("startDate", startDateTime); //"2019-01-01 15:24:49.178+07"
        requestData.put("endDate", endDateTime); //"2019-04-01 15:24:49.178+07"
        requestData.put("periodPromotion",shareData.promotionEffectiveMapped.getPromotionPeriod());
        String url = redisService.get("count_lucky_bag_staging_url");

        HashMap<String,Object> requestMap = new HashMap<>();
        requestMap.put("accountId", shareData.getUserAccountId());
        requestMap.put("data", requestData);

        try {
            SendRequest request = new SendRequest(url,requestMap);

            JSONObject getBagInfo = request.SendRequest();
            JSONObject response = getBagInfo.getJSONObject("data");

            shareData.conditionDetailResult.put("max_rank",response.getInt("maxRank") );
            shareData.conditionDetailResult.put("reward",response.getInt("rewardTime") );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Y";
    }
}