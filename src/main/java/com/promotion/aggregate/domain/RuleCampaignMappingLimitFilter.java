package com.promotion.aggregate.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rule_campaign_mapping_limit_filter")
public class RuleCampaignMappingLimitFilter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "rule_campaign_mapping_limit__id")
    private Integer ruleCampaignMappingLimitId;

    @Column(name = "key_name")
    private String keyName;

    @Column(name = "key_value_type")
    private String keyValueType;

    @Column(name = "key_value")
    private String keyValue;

    @Column(name = "operator")
    private String operator;

    @Column(name = "status")
    private String status;

    @Column(name = "aggregator_type")
    private String aggregatorType;

    public String getAggregatorType() {
        return aggregatorType;
    }

    public void setAggregatorType(String aggregatorType) {
        this.aggregatorType = aggregatorType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRuleCampaignMappingLimitId() {
        return ruleCampaignMappingLimitId;
    }

    public void setRuleCampaignMappingLimitId(Integer ruleCampaignMappingLimitId) {
        this.ruleCampaignMappingLimitId = ruleCampaignMappingLimitId;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyValueType() {
        return keyValueType;
    }

    public void setKeyValueType(String keyValueType) {
        this.keyValueType = keyValueType;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
