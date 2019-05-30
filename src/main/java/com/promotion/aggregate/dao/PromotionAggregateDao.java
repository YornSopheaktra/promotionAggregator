package com.promotion.aggregate.dao;

import com.promotion.aggregate.dto.PromotionEffectiveMapped;
import com.tmc.frmk.core.repo.EntityRepo;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PromotionAggregateDao {

    @Autowired
    private EntityRepo repo;
    
    public List<PromotionEffectiveMapped> getPromotionEffectiveMappedRule(String gateway, String promotionType, String serviceType) throws Exception{
        Session session = repo.getSessionFactory().openSession();
        try{
            String sql ="SELECT sr.ID AS ruleId, sc.ID AS campaignId, sc.promotion_type AS promotionType, " +
                    " sc.promotion_model AS promotionModel, mrc.ID AS mappingRuleCampaignId, scss.service_type__id AS serviceTypeId, " +
                    " scss.gateway AS gateway , string_agg(mrm.id ||'',',') as listRuleMechanicSetId , scc.id As sysCampaignCategoryId,sc.stock_account_id As stockAccountId," +
                    " mrc.effective_start_date AS startEffectiveDate, mrc.effective_end_date AS endEffectiveDate, promotion_period as promotionPeriod " +
                    " FROM sys_campaign AS sc JOIN sys_campaign_service_type_set AS scss ON sc.ID = scss.sys_campaign__id " +
                    " JOIN sys_campaign_category As scc ON scc.id = sc.sys_campaign_category__id " +
                    " JOIN mapping_rules_campaign AS mrc ON ( scss.sys_campaign__id = mrc.sys_campaign__id ) " +
                    " JOIN mapping_rules_mechanics As mrm ON mrm.sys_rule__id = ANY (CAST ( mrc.list_sys_rules__id AS INT [] )) " +
                    " JOIN sys_rules AS sr ON sr.ID = mrm.sys_rule__id " +
                    " WHERE sc.promotion_type =:promotionType " +
                    " AND scss.service_type__id = :serviceType " +
                    " AND now() BETWEEN mrc.effective_start_date AND COALESCE (mrc.effective_end_date,now()) " +
                    " AND scss.gateway =:gateway " +
                    " AND mrc.status = :status " +
                    " AND scss.status = :status " +
                    " AND sr.status = :status " +
                    " AND sc.status = :status " +
                    " GROUP BY sr.ID , sc.ID, sc.promotion_type, sc.promotion_model, mrc.ID, scss.service_type__id, scss.gateway,scc.id,sc.stock_account_id, " +
                    " mrc.effective_start_date, mrc.effective_end_date";
            SQLQuery query = session.createSQLQuery(sql);
            query.setParameter("promotionType",promotionType);
            query.setParameter("serviceType",serviceType);
            query.setParameter("status","active");
            query.setParameter("gateway",gateway);
            query.addEntity(PromotionEffectiveMapped.class);

            if (!query.list().isEmpty())
                return query.list();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return  null;
    }

}
