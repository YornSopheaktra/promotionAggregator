package com.promotion.aggregate.dto;


import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PromotionEffectiveMapped {

    @Id
    private Integer ruleId;
    private Integer campaignId;
    private String promotionType;
    private String promotionModel;
    private Integer mappingRuleCampaignId;
    private String serviceTypeId;
    private String gateway;
    private String listRuleMechanicSetId;
    private  Integer sysCampaignCategoryId;
    private  String stockAccountId;
    private Timestamp startEffectiveDate;
    private Timestamp endEffectiveDate;
    private String promotionPeriod;

    public String getPromotionPeriod() {
		return promotionPeriod;
	}

	public void setPromotionPeriod(String promotionPeriod) {
		this.promotionPeriod = promotionPeriod;
	}

	public Integer getSysCampaignCategoryId() {
        return sysCampaignCategoryId;
    }

    public void setSysCampaignCategoryId(Integer sysCampaignCategoryId) {
        this.sysCampaignCategoryId = sysCampaignCategoryId;
    }

    public String getListRuleMechanicSetId() {
        return listRuleMechanicSetId;
    }

    public void setListRuleMechanicSetId(String listRuleMechanicSetId) {
        this.listRuleMechanicSetId = listRuleMechanicSetId;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public String getPromotionModel() {
        return promotionModel;
    }

    public void setPromotionModel(String promotionModel) {
        this.promotionModel = promotionModel;
    }

    public Integer getMappingRuleCampaignId() {
        return mappingRuleCampaignId;
    }

    public void setMappingRuleCampaignId(Integer mappingRuleCampaignId) {
        this.mappingRuleCampaignId = mappingRuleCampaignId;
    }



    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getStockAccountId() {
        return stockAccountId;
    }

    public void setStockAccountId(String stockAccountId) {
        this.stockAccountId = stockAccountId;
    }

	public Timestamp getStartEffectiveDate() {
		return startEffectiveDate;
	}

	public void setStartEffectiveDate(Timestamp startEffectiveDate) {
		this.startEffectiveDate = startEffectiveDate;
	}

	public Timestamp getEndEffectiveDate() {
		return endEffectiveDate;
	}

	public void setEndEffectiveDate(Timestamp endEffectiveDate) {
		this.endEffectiveDate = endEffectiveDate;
	}
}
