package com.promotion.aggregate.dao;

import com.promotion.aggregate.daoI.SysRuleMechanicSetDaoI;
import com.promotion.aggregate.domain.SysRuleMechanicSet;
import com.tmc.frmk.core.repo.EntityRepo;
import com.tmc.frmk.core.tools.hsql.BaseCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysRuleMechanicSetDao implements SysRuleMechanicSetDaoI {

    @Autowired
    private EntityRepo repo;

    @Override
    public SysRuleMechanicSet getSysRuleMechanicSetByMappingRuleId(Integer id) {
        try{
            BaseCriteria<SysRuleMechanicSet> criteria = new BaseCriteria<>(SysRuleMechanicSet.class);
            criteria.addCriterion(Restrictions.eq("status","active"));
            criteria.addCriterion(Restrictions.eq("mappingRulesMechanicsId",id));
            return repo.list(criteria).get(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
