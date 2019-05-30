package com.promotion.aggregate.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sys_action")
public class SysAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "sys_action_type__id")
    private Integer sysActionTypeId;

    @Column(name = "mapping_rules_campaign__id")
    private Integer mappingRulesCampaignId;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_type")
    private String userType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysActionTypeId() {
        return sysActionTypeId;
    }

    public void setSysActionTypeId(Integer sysActionTypeId) {
        this.sysActionTypeId = sysActionTypeId;
    }

    public Integer getMappingRulesCampaignId() {
        return mappingRulesCampaignId;
    }

    public void setMappingRulesCampaignId(Integer mappingRulesCampaignId) {
        this.mappingRulesCampaignId = mappingRulesCampaignId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
