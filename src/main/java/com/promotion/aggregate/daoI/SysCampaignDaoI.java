package com.promotion.aggregate.daoI;

import com.promotion.aggregate.domain.SysCampaign;

import java.util.List;

public interface SysCampaignDaoI {

    public SysCampaign getSysCampaignById(Integer id);
    public List<SysCampaign> getAllActiveSysCampaign();
}
