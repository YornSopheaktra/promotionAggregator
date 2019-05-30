package com.promotion.aggregate.dao;

import com.promotion.aggregate.daoI.SysRmConditionDetailsDaoI;
import com.promotion.aggregate.domain.SysRmConditionDetails;
import com.tmc.frmk.core.repo.EntityRepo;
import com.tmc.frmk.core.tools.hsql.BaseCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRmConditionDetailsDao implements SysRmConditionDetailsDaoI {

    @Autowired
    private EntityRepo repo;
    @Override
    public List<SysRmConditionDetails> getSysRmConditionDetails(List<Integer> mappingId) {
        try {
            BaseCriteria<SysRmConditionDetails> criteria = new BaseCriteria<>(SysRmConditionDetails.class);
            criteria.addCriterion(Restrictions.in("sysConditionId",mappingId))
                    .addCriterion(Restrictions.eq("status","active"));
            return repo.list(criteria);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
