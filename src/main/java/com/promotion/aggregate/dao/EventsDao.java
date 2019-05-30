package com.promotion.aggregate.dao;

import com.promotion.aggregate.daoI.EventsDaoI;
import com.tmc.frmk.core.repo.EntityRepo;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventsDao implements EventsDaoI {

    @Autowired
    private EntityRepo repo;

    @Override
    public Integer getCountSummaryEvent(Integer ruleCampaignMappingId, String serviceTypeId, String gateWay, String additionFields) {
        Session session = repo.getSessionFactory().openSession();
        try{
            String sql ="SELECT count(*) from events where status='TS' AND service_type__id = :serviceTypeId AND request_gateway = :gateWay" + additionFields;
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("serviceTypeId", serviceTypeId);
            query.setParameter("gateWay", gateWay);

            Integer total = Integer.valueOf(query.list().get(0).toString());
            return  Integer.valueOf(total);

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
