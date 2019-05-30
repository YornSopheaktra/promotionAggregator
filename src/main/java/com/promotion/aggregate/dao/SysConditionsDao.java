package com.promotion.aggregate.dao;

import com.promotion.aggregate.daoI.SysConditionsDaoI;
import com.promotion.aggregate.domain.SysConditions;
import com.tmc.frmk.core.repo.EntityRepo;
import com.tmc.frmk.core.tools.hsql.BaseCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysConditionsDao implements SysConditionsDaoI {
    @Autowired
    private EntityRepo repo;
    @Override
    public List<SysConditions> getListSysConditions(Integer sysRuleMechanicSetId) {
        BaseCriteria<SysConditions> criteria =new BaseCriteria<>(SysConditions.class);
        criteria.addCriterion(Restrictions.eq("status","active"));
        criteria.addCriterion(Restrictions.eq("sysRuleMechanicSetId",sysRuleMechanicSetId));
        criteria.addOrder(Order.asc("sequenceNum"));
        return repo.list(criteria);
    }
}
