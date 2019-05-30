package com.promotion.aggregate.service;

import com.promotion.aggregate.domain.*;
import com.promotion.aggregate.dto.ActionMapping;
import com.promotion.aggregate.dto.ConditionResults;
import com.promotion.aggregate.dto.PromotionEffectiveMapped;
import com.tmc.frmk.core.domain.request.RequestDTO;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class PromotionShareData {
    public RequestDTO promotionRequestDTO;
    public List<SysCampaign> listSysCampaign = null;
    public List<SysMechanics> listSysMechanics=null;
    public List<SysRuleMechanicSet> listSysRuleMechanicSet=null;
    public List<SysConditions> listSysConditions=null;
    public SysRmConditionDetails sysRmConditionDetails=null;
    public List<ConditionResults> conditionResultsList =null;
    public HashMap<String,Object> conditionDetailResult=null;
    public PromotionEffectiveMapped promotionEffectiveMapped =null;
    private String userAccountId;
    public ActionMapping actionMapping=null;

    public String getIsGetPromotion() {
        return isGetPromotion;
    }

    public void setIsGetPromotion(String isGetPromotion) {
        this.isGetPromotion = isGetPromotion;
    }

    private String isGetPromotion = "N";

    public List<SysActionData> sysActionDataHistory = null;

    public Events events =null;

    public String getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(String userAccountId) {
        this.userAccountId = userAccountId;
    }
}
