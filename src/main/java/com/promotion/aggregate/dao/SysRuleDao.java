package com.promotion.aggregate.dao;

import com.promotion.aggregate.daoI.SysRuleDaoI;
import com.promotion.aggregate.domain.SysRules;
import com.tmc.frmk.core.repo.EntityRepo;
import com.tmc.frmk.core.tools.hsql.BaseCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRuleDao implements SysRuleDaoI {

    @Autowired
    private EntityRepo repo;

    @Override
    public SysRules getRuleByid(Integer id) {
        try{
            BaseCriteria<SysRules> criteria = new BaseCriteria<>(SysRules.class);
            criteria.addCriterion(Restrictions.eq("status","active"));
            criteria.addCriterion(Restrictions.eq("id",id));
            return repo.list(criteria).get(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SysRules> getListRule() {
        BaseCriteria<SysRules> criteria = new BaseCriteria<>(SysRules.class);
        criteria.addCriterion(Restrictions.eq("status","active"));
        return repo.list(criteria);
    }
}
