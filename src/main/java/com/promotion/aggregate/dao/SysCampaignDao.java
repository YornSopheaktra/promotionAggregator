package com.promotion.aggregate.dao;

import com.promotion.aggregate.daoI.SysCampaignDaoI;
import com.promotion.aggregate.domain.SysCampaign;
import com.tmc.frmk.core.repo.EntityRepo;
import com.tmc.frmk.core.tools.hsql.BaseCriteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysCampaignDao implements SysCampaignDaoI {

    @Autowired
    private EntityRepo repo;

    @Override
    public SysCampaign getSysCampaignById(Integer id) {
        try{
            BaseCriteria<SysCampaign> criteria = new BaseCriteria<>(SysCampaign.class);
            criteria.addCriterion(Restrictions.eq("status","active"));
            criteria.addCriterion(Restrictions.eq("id",id));
            return repo.list(criteria).get(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<SysCampaign> getAllActiveSysCampaign(){
        Session session = repo.getSessionFactory().openSession();
        try{
            String sql ="SELECT sc.* FROM sys_campaign sc JOIN sys_campaign_category scc ON sc.sys_campaign_category__id = scc.id " +
                    " JOIN mapping_rules_campaign mrc ON mrc.sys_campaign__id =sc.id" +
                    " WHERE sc.status = :status " +
                    " AND scc.status= :status " +
                    " AND mrc.status= :status ";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("status","active");
            query.addEntity(SysCampaign.class);

            if (!query.list().isEmpty())
                return query.list();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }
}
