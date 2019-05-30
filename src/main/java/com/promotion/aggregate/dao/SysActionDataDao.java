package com.promotion.aggregate.dao;

import com.promotion.aggregate.daoI.SysActionDataDaoI;
import com.promotion.aggregate.domain.SysActionData;
import com.tmc.frmk.core.repo.EntityRepo;
import com.tmc.frmk.core.tools.hsql.BaseCriteria;
import org.bouncycastle.asn1.isismtt.x509.Restriction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysActionDataDao implements SysActionDataDaoI {

    @Autowired
    private EntityRepo repo;

    @Override
    public List<SysActionData> getListActionDataByActionId(Integer actionId) {
        BaseCriteria<SysActionData> criteria = new BaseCriteria<>(SysActionData.class);
        criteria.addCriterion(Restrictions.eq("sysActionId",actionId));
        criteria.addCriterion(Restrictions.eq("status","active"));
        return repo.list(criteria);
    }
}
