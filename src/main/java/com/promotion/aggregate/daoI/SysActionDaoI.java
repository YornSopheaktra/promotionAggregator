package com.promotion.aggregate.daoI;

import com.promotion.aggregate.domain.SysAction;

public interface SysActionDaoI {

    public SysAction getSysActionByMappedCampaignId(Integer mappedCampaignId);
}
