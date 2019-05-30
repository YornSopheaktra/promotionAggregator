package com.promotion.aggregate.dao;

import com.promotion.aggregate.daoI.MappingRuleMechanicsDaoI;
import com.promotion.aggregate.domain.MappingRulesMechanics;
import com.tmc.frmk.core.repo.EntityRepo;
import com.tmc.frmk.core.tools.hsql.BaseCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MappingRuleMechanicsDao implements MappingRuleMechanicsDaoI {

    @Autowired
    private EntityRepo repo;
    @Override
    public MappingRulesMechanics getMappingRuleMechanicsByid(Integer id) {
        try{
            BaseCriteria<MappingRulesMechanics> criteria = new BaseCriteria<>(MappingRulesMechanics.class);
            criteria.addCriterion(Restrictions.eq("status", "active"));
            criteria.addCriterion(Restrictions.eq("id", id));
            return repo.list(criteria).get(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MappingRulesMechanics> getListMappingRuleMechanics() {
        BaseCriteria<MappingRulesMechanics> criteria = new BaseCriteria<>(MappingRulesMechanics.class);
        criteria.addCriterion(Restrictions.eq("status", "active"));
        return repo.list(criteria);
    }
}
