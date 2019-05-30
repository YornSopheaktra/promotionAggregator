package com.promotion.aggregate.dao;

import com.promotion.aggregate.daoI.SysMechanicsDaoI;
import com.promotion.aggregate.domain.SysMechanics;
import com.tmc.frmk.core.repo.EntityRepo;
import com.tmc.frmk.core.tools.hsql.BaseCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMechanicsDao implements SysMechanicsDaoI {

    @Autowired
    private EntityRepo repo;

    @Override
    public SysMechanics getSysMechanicsById(Integer id) {
        try{
            BaseCriteria<SysMechanics> criteria =new BaseCriteria<>(SysMechanics.class);
            criteria.addCriterion(Restrictions.eq("status","active"));
            criteria.addCriterion(Restrictions.eq("id",id));
            return repo.list(criteria).get(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SysMechanics> getListSysMechanics() {
        BaseCriteria<SysMechanics> criteria =new BaseCriteria<>(SysMechanics.class);
        criteria.addCriterion(Restrictions.eq("status","active"));
        return repo.list(criteria);
    }
}
