package com.promotion.aggregate.dao;
import com.promotion.aggregate.domain.SysActionType;
import com.promotion.aggregate.dto.ActionMapping;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tmc.frmk.core.repo.EntityRepo;

@Repository
public class SysActionTypeDao {

	@Autowired
	private EntityRepo repo;

	public SysActionType getSysActionType(Integer sysCampaignId){
		Session session = repo.getSessionFactory().openSession();

		try{
			String sql = "SELECT sat.* FROM sys_action sa " +
					"JOIN mapping_rules_campaign mrc on sa.mapping_rules_campaign__id = mrc.id " +
					"JOIN sys_action_type sat ON sa.sys_action_type__id = sat.id " +
					"WHERE mrc.sys_campaign__id = :sysCampaignId " +
					"AND sa.status='active' " +
					"AND mrc.status='active'";
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter("sysCampaignId", sysCampaignId);
			query.addEntity(SysActionType.class);
			query.setMaxResults(1);
			return (SysActionType) query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		return null;
	}

	public ActionMapping getActionMapping(Integer sysCampaignId){
		Session session = repo.getSessionFactory().openSession();

		try{
			String sql = "SELECT sa.id, sat.id As actionTypeId, sat.name As actionTypeName, sa.mapping_rules_campaign__id as mappingRuleCampaignId FROM sys_action sa " +
					" JOIN mapping_rules_campaign mrc on sa.mapping_rules_campaign__id = mrc.id   " +
					" JOIN sys_action_type sat ON sa.sys_action_type__id = sat.id  " +
					" WHERE mrc.sys_campaign__id = :sysCampaignId " +
					" AND sa.status='active' " +
					" AND mrc.status='active' ";
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter("sysCampaignId", sysCampaignId);
			query.addEntity(ActionMapping.class);
			query.setMaxResults(1);
			return (ActionMapping) query.uniqueResult();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}	

		return null;
	}
}
