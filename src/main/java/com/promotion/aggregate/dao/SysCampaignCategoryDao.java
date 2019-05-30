package com.promotion.aggregate.dao;

import com.promotion.aggregate.daoI.SysCampaignCategoryDaoI;
import com.promotion.aggregate.domain.SysCampaignCategory;
import com.tmc.frmk.core.repo.EntityRepo;
import com.tmc.frmk.core.tools.hsql.BaseCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SysCampaignCategoryDao implements SysCampaignCategoryDaoI {
    @Autowired
    private EntityRepo repo;
    @Override
    public SysCampaignCategory getSysCampaignCategoryById(Integer id) {
        try{
            BaseCriteria<SysCampaignCategory> criteria = new BaseCriteria<>(SysCampaignCategory.class);
            criteria.addCriterion(Restrictions.eq("status","active"));
            criteria.addCriterion(Restrictions.eq("id",id));
            return repo.list(criteria).get(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SysCampaignCategory> getListSysCampaignCategory() {
       BaseCriteria<SysCampaignCategory> criteria = new BaseCriteria<>(SysCampaignCategory.class);
       criteria.addCriterion(Restrictions.eq("status","active"));
       return repo.list(criteria);
    }
}
