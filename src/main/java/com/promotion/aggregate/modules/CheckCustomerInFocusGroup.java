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

@Modules("check_customer_in_focus_group")
public class CheckCustomerInFocusGroup extends AbstractPromoAggregatorService {

	@Autowired
	private RedisService redisService;

	@Override
	protected Object checkProcess(RequestDTO req, PromotionShareData shareData) {
		try {
			JSONObject requestUserProfile = new JSONObject();
			requestUserProfile.put("requestGateWay", req.getRequestGateWay());
			requestUserProfile.put("serviceId", "get_account");
			requestUserProfile.put("accountId", req.getAccountId());
			//Debug.debugObject("Redis Service: ", BeanUtils.getBean(RedisService.class));
			String url =BeanUtils.getBean(RedisService.class).get("get_user_account_in_focus_group_url_staging");
			SendRequest request = new SendRequest(url, requestUserProfile);
			JSONObject resUserProfile = request.SendRequest();
			JSONObject resData = resUserProfile.getJSONObject("data");
			Debug.debugObject("check_customer_in_focus_group", resData);
			shareData.conditionDetailResult.put("in_focus_list", resData.getString("in_focus_list"));
			return resData.getString("in_focus_list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
