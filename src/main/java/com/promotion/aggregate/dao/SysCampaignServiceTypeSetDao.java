package com.promotion.aggregate.dao;

import com.promotion.aggregate.daoI.SysCampaignServiceTypeSetDaoI;
import com.promotion.aggregate.domain.SysCampaignServiceTypeSet;
import com.tmc.frmk.core.repo.EntityRepo;
import com.tmc.frmk.core.tools.hsql.BaseCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysCampaignServiceTypeSetDao implements SysCampaignServiceTypeSetDaoI {

    @Autowired
    private EntityRepo repo;

    @Override
    public SysCampaignServiceTypeSet getCampaignMappedServiceType(Integer sysCampaignId, String serviceType, String gateway) {
        try{
            BaseCriteria<SysCampaignServiceTypeSet> criteria = new BaseCriteria<>(SysCampaignServiceTypeSet.class);
            criteria.addCriterion(Restrictions.eq("sysCampaignId",sysCampaignId));
            criteria.addCriterion(Restrictions.eq("status","active"));
            criteria.addCriterion(Restrictions.eq("gateway", gateway));
            criteria.addCriterion(Restrictions.eq("serviceTypeId",serviceType));
            criteria.setEntityClass(SysCampaignServiceTypeSet.class);
            return repo.list(criteria).get(0);
        }catch (Exception e){
            //e.printStackTrace();
        }
        return null;
    }
}
