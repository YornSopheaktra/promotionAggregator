package com.promotion.aggregate.daoI;

import com.promotion.aggregate.domain.SysCampaignServiceTypeSet;

import java.util.List;

public interface SysCampaignServiceTypeSetDaoI {

    public SysCampaignServiceTypeSet getCampaignMappedServiceType(Integer sysCampaignId, String serviceType,String gateway);
}
