package com.promotion.aggregate.daoI;

import com.promotion.aggregate.domain.RuleCampaignMappingLimit;

public interface RuleCampaignMappingLimitDaoI {

    public RuleCampaignMappingLimit getMappingLimitByMappingCampaignId(Integer mappingCampaignId);
}
