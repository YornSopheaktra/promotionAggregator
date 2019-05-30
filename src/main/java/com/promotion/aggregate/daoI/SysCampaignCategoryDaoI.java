package com.promotion.aggregate.daoI;

import com.promotion.aggregate.domain.SysCampaignCategory;

import java.util.List;

public interface SysCampaignCategoryDaoI {
    SysCampaignCategory getSysCampaignCategoryById(Integer id);
    List<SysCampaignCategory> getListSysCampaignCategory();
}
