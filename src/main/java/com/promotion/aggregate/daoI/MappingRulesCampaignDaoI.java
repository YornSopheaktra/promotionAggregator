package com.promotion.aggregate.daoI;

import com.promotion.aggregate.domain.MappingRulesCampaign;

import java.util.List;

public interface MappingRulesCampaignDaoI {
    MappingRulesCampaign getMappingRulesCampaignById(Integer id);
    List<MappingRulesCampaign> getListMappingRulesCampaign();

}
