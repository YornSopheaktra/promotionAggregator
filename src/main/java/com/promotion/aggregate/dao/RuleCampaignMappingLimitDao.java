package com.promotion.aggregate.dao;

import com.promotion.aggregate.daoI.RuleCampaignMappingLimitDaoI;
import com.promotion.aggregate.domain.RuleCampaignMappingLimit;
import com.tmc.frmk.core.repo.EntityRepo;
import com.tmc.frmk.core.tools.hsql.BaseCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;

@Service
public class RuleCampaignMappingLimitDao implements RuleCampaignMappingLimitDaoI {

    @Autowired
    private EntityRepo repo;

    @Override
    public RuleCampaignMappingLimit getMappingLimitByMappingCampaignId(Integer mappingCampaignId) {
        try{
            BaseCriteria<RuleCampaignMappingLimit> criteria = new BaseCriteria<>(RuleCampaignMappingLimit.class);
            criteria.addCriterion(Restrictions.eq("status", "active"));
            criteria.addCriterion(Restrictions.eq("mappingRuleCampaignId", mappingCampaignId));
            return repo.list(criteria).get(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
