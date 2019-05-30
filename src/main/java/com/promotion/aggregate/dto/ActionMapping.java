package com.promotion.aggregate.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ActionMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer actionTypeId;
    private String actionTypeName;
    private  Integer mappingRuleCampaignId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActionTypeId() {
        return actionTypeId;
    }

    public void setActionTypeId(Integer actionTypeId) {
        this.actionTypeId = actionTypeId;
    }

    public String getActionTypeName() {
        return actionTypeName;
    }

    public void setActionTypeName(String actionTypeName) {
        this.actionTypeName = actionTypeName;
    }

    public Integer getMappingRuleCampaignId() {
        return mappingRuleCampaignId;
    }

    public void setMappingRuleCampaignId(Integer mappingRuleCampaignId) {
        this.mappingRuleCampaignId = mappingRuleCampaignId;
    }
}
