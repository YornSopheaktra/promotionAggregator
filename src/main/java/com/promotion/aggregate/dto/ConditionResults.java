package com.promotion.aggregate.dto;

public class ConditionResults {

    private Integer id;
    private Integer sysConditionId;
    private String keyValue;
    private String keyResult;
    private String type;
    private boolean returnResult;
    private String aggregateType;

    public String getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysConditionId() {
        return sysConditionId;
    }

    public void setSysConditionId(Integer sysConditionId) {
        this.sysConditionId = sysConditionId;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getKeyResult() {
        return keyResult;
    }

    public void setKeyResult(String keyResult) {
        this.keyResult = keyResult;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isReturnResult() {
        return returnResult;
    }

    public void setReturnResult(boolean returnResult) {
        this.returnResult = returnResult;
    }
}
