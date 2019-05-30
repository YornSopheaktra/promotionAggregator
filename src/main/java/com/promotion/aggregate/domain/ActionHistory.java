package com.promotion.aggregate.domain;

import javax.persistence.*;

@Entity
@Table(name = "action_history")
public class ActionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "sys_event__id")
    private Integer sysEventId;

    @Column(name = "sys_action_type__id")
    private Integer sysActionTypeId;

    @Column(name = "status")
    private String status;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "mapping_rules_campaign__id")
    private Integer mappingRulesCampaignId;

    @Column(name = "sys_action__id")
    private Integer sysActionId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysEventId() {
        return sysEventId;
    }

    public void setSysEventId(Integer sysEventId) {
        this.sysEventId = sysEventId;
    }

    public Integer getSysActionTypeId() {
        return sysActionTypeId;
    }

    public void setSysActionTypeId(Integer sysActionTypeId) {
        this.sysActionTypeId = sysActionTypeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Integer getMappingRulesCampaignId() {
        return mappingRulesCampaignId;
    }

    public void setMappingRulesCampaignId(Integer mappingRulesCampaignId) {
        this.mappingRulesCampaignId = mappingRulesCampaignId;
    }

    public Integer getSysActionId() {
        return sysActionId;
    }

    public void setSysActionId(Integer sysActionId) {
        this.sysActionId = sysActionId;
    }
}
