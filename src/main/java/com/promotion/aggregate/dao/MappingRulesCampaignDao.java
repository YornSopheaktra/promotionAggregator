package com.promotion.aggregate.dao;

import com.promotion.aggregate.daoI.MappingRulesCampaignDaoI;
import com.promotion.aggregate.domain.MappingRulesCampaign;
import com.tmc.frmk.core.repo.EntityRepo;
import com.tmc.frmk.core.tools.hsql.BaseCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MappingRulesCampaignDao implements MappingRulesCampaignDaoI {

    @Autowired
    private EntityRepo repo;

    @Override
    public MappingRulesCampaign getMappingRulesCampaignById(Integer id) {
        try {
            BaseCriteria<MappingRulesCampaign> criteria = new BaseCriteria<>(MappingRulesCampaign.class);
            criteria.addCriterion(Restrictions.eq("status", "active"))
                    .addCriterion(Restrictions.eq("sysCampaignId", id));
            return repo.list(criteria).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MappingRulesCampaign> getListMappingRulesCampaign() {
        BaseCriteria<MappingRulesCampaign> criteria = new BaseCriteria<>(MappingRulesCampaign.class);
        criteria.addCriterion(Restrictions.eq("status", "active"));
        return repo.list(criteria);
    }

    public List<MappingRulesCampaign> getMappingRulesCampaign(Integer sysSetPromotionId, Date now){
        BaseCriteria<MappingRulesCampaign> criteria = new BaseCriteria<>(MappingRulesCampaign.class);
        criteria.addCriterion(Restrictions.eq("status", "active"));
        criteria.addCriterion(Restrictions.eq("sysCampaignId", sysSetPromotionId));
        criteria.addCriterion(Restrictions.le("effectiveStartDate", now));
        criteria.addCriterion(Restrictions.or(
               Restrictions.ge("effectiveEndDate", now),
               Restrictions.isNull("effectiveEndDate")));
        return repo.list(criteria);
    }
}
