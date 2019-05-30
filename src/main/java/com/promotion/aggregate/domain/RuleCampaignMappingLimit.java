package com.promotion.aggregate.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rule_campaign_mapping_limit")
public class RuleCampaignMappingLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "sys_action__id")
    private Integer sysActionId;

    @Column(name = "mapping_rule_campaign__id")
    private Integer mappingRuleCampaignId;

    @Column(name = "limit_type")
    private String limitType;

    @Column(name = "limit_value")
    private String limitValue;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "updated_by")
    private Integer updatedBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysActionId() {
        return sysActionId;
    }

    public void setSysActionId(Integer sysActionId) {
        this.sysActionId = sysActionId;
    }

    public Integer getMappingRuleCampaignId() {
        return mappingRuleCampaignId;
    }

    public void setMappingRuleCampaignId(Integer mappingRuleCampaignId) {
        this.mappingRuleCampaignId = mappingRuleCampaignId;
    }

    public String getLimitType() {
        return limitType;
    }

    public void setLimitType(String limitType) {
        this.limitType = limitType;
    }

    public String getLimitValue() {
        return limitValue;
    }

    public void setLimitValue(String limitValue) {
        this.limitValue = limitValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }
}
