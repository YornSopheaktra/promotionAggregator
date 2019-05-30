package com.promotion.aggregate.dao;

import com.promotion.aggregate.daoI.RuleCampaignMappingLimitFilterDaoI;
import com.promotion.aggregate.domain.RuleCampaignMappingLimitFilter;
import com.tmc.frmk.core.repo.EntityRepo;
import com.tmc.frmk.core.tools.hsql.BaseCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleCampaignMappingLimitFilterDao implements RuleCampaignMappingLimitFilterDaoI {

    @Autowired
    private EntityRepo repo;

    @Override
    public List<RuleCampaignMappingLimitFilter> getLimitFilterByLimitId(Integer limitId) {

        try {
            BaseCriteria<RuleCampaignMappingLimitFilter> criteria = new BaseCriteria<>(RuleCampaignMappingLimitFilter.class);
            criteria.addCriterion(Restrictions.eq("status","active"));
            criteria.addCriterion(Restrictions.eq("ruleCampaignMappingLimitId", limitId));
            return repo.list(criteria);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
