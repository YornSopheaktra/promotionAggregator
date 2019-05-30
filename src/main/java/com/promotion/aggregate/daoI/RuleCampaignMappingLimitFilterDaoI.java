package com.promotion.aggregate.daoI;

import com.promotion.aggregate.domain.RuleCampaignMappingLimitFilter;

import java.util.List;

public interface RuleCampaignMappingLimitFilterDaoI {

    public List<RuleCampaignMappingLimitFilter> getLimitFilterByLimitId(Integer limitId);
}
