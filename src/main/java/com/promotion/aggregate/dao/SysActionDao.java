package com.promotion.aggregate.dao;

import com.promotion.aggregate.daoI.SysActionDaoI;
import com.promotion.aggregate.domain.SysAction;
import com.tmc.frmk.core.repo.EntityRepo;
import com.tmc.frmk.core.tools.hsql.BaseCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysActionDao implements SysActionDaoI {
    @Autowired
    private EntityRepo repo;

    @Override
    public SysAction getSysActionByMappedCampaignId(Integer mappedCampaignId) {
        try{
            BaseCriteria<SysAction> criteria =new BaseCriteria<>(SysAction.class);
            criteria.addCriterion(Restrictions.eq("status","active"));
            criteria.addCriterion(Restrictions.eq("mappingRulesCampaignId", mappedCampaignId));
            return repo.list(criteria).get(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
